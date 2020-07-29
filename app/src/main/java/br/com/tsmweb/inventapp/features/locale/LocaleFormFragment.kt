package br.com.tsmweb.inventapp.features.locale

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
import br.com.tsmweb.inventapp.databinding.FragmentLocaleFormBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LocaleFormFragment : DialogFragment() {

    private val TAG: String = LocaleFormFragment::class.java.simpleName

    private lateinit var binding: FragmentLocaleFormBinding
    private val viewModel: LocaleFormViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocaleFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<LocaleBinding>(EXTRA_LOCALE)?.let {
            viewModel.setLocale(it)
            binding.locale = it
        }

        subscriberViewModalObservable()

        binding.btnLocaleOK.setOnClickListener {
            savePlace()
        }

        binding.btnLocaleCancel.setOnClickListener {
            dismiss()
        }

        binding.edtName.setOnEditorActionListener{ _, i, _ ->
            handleKeyboardEvent(i)
        }

        dialog?.setTitle(R.string.title_new_locale)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    private fun subscriberViewModalObservable() {
        viewModel.saveState().observe(viewLifecycleOwner, Observer { state ->
            state?.run {
                when (status) {
                    ViewState.Status.LOADING -> {
                        Log.d(TAG, "Process is loading")
                    }
                    ViewState.Status.SUCCESS -> {
                        dialog?.dismiss()
                    }
                    ViewState.Status.ERROR -> {
                        Log.d(TAG, error.toString())
                        Toast.makeText(requireContext(), R.string.message_error_save_locale, Toast.LENGTH_SHORT).show()
                    }
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
        val locale = binding.locale!!

        if (locale.code.isBlank()) {
            binding.edtCode.error = getString(R.string.message_form_enter_code)
            binding.edtCode.requestFocus()
            return
        }

        if (locale.name.isBlank()) {
            binding.edtName.error = getString(R.string.message_form_enter_name)
            binding.edtName.requestFocus()
            return
        }

        viewModel.saveLocale()
    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG)
        }
    }

    companion object {
        private const val DIALOG_TAG = "formDialog"
        private const val EXTRA_LOCALE = "locale"

        fun newInstance(locale: LocaleBinding) = LocaleFormFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_LOCALE, locale)
            }
        }
    }

}