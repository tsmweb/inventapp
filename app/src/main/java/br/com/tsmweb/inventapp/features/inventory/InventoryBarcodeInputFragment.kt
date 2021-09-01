package br.com.tsmweb.inventapp.features.inventory

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.Constants.EXTRA_BARCODE
import br.com.tsmweb.inventapp.common.extensions.closeKeyboard
import br.com.tsmweb.inventapp.common.extensions.showKeyboard
import br.com.tsmweb.inventapp.databinding.FragmentInventoryBarcodeInputBinding

class InventoryBarcodeInputFragment : DialogFragment() {

    private val TAG = InventoryBarcodeInputFragment::class.simpleName

    private lateinit var binding: FragmentInventoryBarcodeInputBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventoryBarcodeInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireDialog().setTitle(R.string.title_barcode)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnOK.setOnClickListener {
            if (binding.edtBarcode.text?.isBlank() == true) {
                binding.edtBarcode.error = getString(R.string.message_form_enter_barcode)
                binding.edtBarcode.requestFocus()
            } else {
                setFragmentResult(BARCODE_INPUT_REQUEST_KEY, bundleOf(EXTRA_BARCODE to binding.edtBarcode.text.toString()))
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.edtBarcode.post {
            binding.edtBarcode.requestFocus()
            (binding.edtBarcode.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                showSoftInput(binding.edtBarcode, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        (binding.edtBarcode.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            if (isActive) {
                hideSoftInputFromWindow(binding.edtBarcode.windowToken, 0)
            }
        }

        super.onDismiss(dialog)
    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(BARCODE_INPUT_DIALOG_TAG) == null) {
            show(fm, BARCODE_INPUT_DIALOG_TAG)
        }
    }

    companion object {
        private const val BARCODE_INPUT_DIALOG_TAG = "inventoryBarcodeInputDialog"
        const val BARCODE_INPUT_REQUEST_KEY = "inventoryBarcodeInputRequestKey"

        fun newInstance() = InventoryBarcodeInputFragment()
    }

}