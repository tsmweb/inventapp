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
import kotlinx.android.synthetic.main.fragment_inventory_barcode_input.*

class InventoryBarcodeInputFragment : DialogFragment() {

    private val TAG = InventoryBarcodeInputFragment::class.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inventory_barcode_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireDialog().setTitle(R.string.title_barcode)

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnOK.setOnClickListener {
            if (edtBarcode.text?.isBlank() == true) {
                edtBarcode.error = getString(R.string.message_form_enter_barcode)
                edtBarcode.requestFocus()
            } else {
                setFragmentResult(BARCODE_INPUT_REQUEST_KEY, bundleOf(EXTRA_BARCODE to edtBarcode.text.toString()))
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        edtBarcode.post {
            edtBarcode.requestFocus()
            (edtBarcode.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                showSoftInput(edtBarcode, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        (edtBarcode.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            if (isActive) {
                toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
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