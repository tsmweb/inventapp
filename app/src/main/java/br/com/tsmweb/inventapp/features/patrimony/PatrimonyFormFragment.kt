package br.com.tsmweb.inventapp.features.patrimony

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentPatrimonyFormBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PatrimonyFormFragment : DialogFragment() {

    private val TAG = PatrimonyFormFragment::class.simpleName

    private lateinit var binding: FragmentPatrimonyFormBinding
    private val viewModel: PatrimonyFormViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatrimonyFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<PatrimonyBinding>(EXTRA_PATRIMONY)?.let {
            viewModel.setPatrimony(it)
            binding.patrimony = it
        }

        subscriberViewModalObservable()

        binding.btnOK.setOnClickListener {
            savePatrimony()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.edtName.setOnEditorActionListener{ _, i, _ ->
            handleKeyboardEvent(i)
        }

        dialog?.setTitle(R.string.title_new_patrimony)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    private fun handleKeyboardEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            savePatrimony()
            return true
        }

        return false
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
                    Toast.makeText(requireContext(), R.string.message_error_save_patrimony, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun savePatrimony() {
        val patrimony = binding.patrimony!!

        if (patrimony.code.isBlank()) {
            binding.edtCode.error = getString(R.string.message_form_enter_code)
            binding.edtCode.requestFocus()
            return
        }

        if (patrimony.name.isBlank()) {
            binding.edtName.error = getString(R.string.message_form_enter_name)
            binding.edtName.requestFocus()
            return
        }

        if (patrimony.dependency.isBlank()) {
            binding.edtDependency.error = getString(R.string.message_form_enter_dependency)
            binding.edtDependency.requestFocus()
            return
        }

        viewModel.savePatrimony()
    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG)
        }
    }

    companion object {
        private const val DIALOG_TAG = "formDialog"
        private const val EXTRA_PATRIMONY = "patrimony"

        fun newInstance(patrimony: PatrimonyBinding) = PatrimonyFormFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_PATRIMONY, patrimony)
            }
        }
    }

}