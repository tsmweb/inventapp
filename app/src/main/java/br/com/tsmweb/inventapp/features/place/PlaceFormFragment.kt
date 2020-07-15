package br.com.tsmweb.inventapp.features.place

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentPlaceFormBinding
import br.com.tsmweb.inventapp.features.place.binding.PlaceBinding
import kotlinx.android.synthetic.main.fragment_place_form.*
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceFormFragment : DialogFragment() {

    private val TAG: String = PlaceFormFragment::class.java.simpleName

    private lateinit var binding: FragmentPlaceFormBinding
    private val viewModel: PlaceFormViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place_form, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<PlaceBinding>(EXTRA_PLACE)?.let {
            viewModel.setPlace(it)
            binding.place = it
        }

        subscriberViewModalObservable()

        btnPlaceOK.setOnClickListener {
            savePlace()
        }

        btnPlaceCancel.setOnClickListener {
            dismiss()
        }

        edtName.setOnEditorActionListener{ _, i, _ ->
            handleKeyboardEvent(i)
        }

        dialog?.setTitle(R.string.action_new_place)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    private fun subscriberViewModalObservable() {
        viewModel.saveState().observe(viewLifecycleOwner, Observer { state ->
            when (state.status) {
                ViewState.Status.LOADING -> {
                    Log.d(TAG, "Process is loading")
                }
                ViewState.Status.SUCCESS -> {
                    dialog?.dismiss()
                }
                ViewState.Status.ERROR -> {
                    Log.d(TAG, state.error.toString())
                    Toast.makeText(requireContext(), R.string.message_error_save_place, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun handleKeyboardEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            savePlace()
            return true
        }

        return false
    }

    private fun savePlace() {
        val place = binding.place!!

        if (place.code.isBlank()) {
            binding.edtCode.error = getString(R.string.message_enter_place_code)
            binding.edtCode.requestFocus()
            return
        }

        if (place.name.isBlank()) {
            binding.edtName.error = getString(R.string.message_enter_place_name)
            binding.edtName.requestFocus()
            return
        }

        viewModel.savePlace()
    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG)
        }
    }

    companion object {
        private const val DIALOG_TAG = "editDialog"
        private const val EXTRA_PLACE = "place"

        fun newInstance(place: PlaceBinding) = PlaceFormFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_PLACE, place)
            }
        }
    }

}