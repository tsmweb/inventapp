package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.Constants.EXTRA_BARCODE
import kotlinx.android.synthetic.main.fragment_inventory_barcode_input.*

class InventoryBarcodeInputFragment : DialogFragment() {

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
                setFragmentResult(INPUT_BARCODE_REQUEST_KEY, bundleOf(EXTRA_BARCODE to edtBarcode.text.toString()))
                dismiss()
            }
        }
    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(INPUT_BARCODE_DIALOG_TAG) == null) {
            show(fm, INPUT_BARCODE_DIALOG_TAG)
        }
    }

    companion object {
        private const val INPUT_BARCODE_DIALOG_TAG = "inputBarcodeDialog"
        const val INPUT_BARCODE_REQUEST_KEY = "inputBarcodeRequestKey"

        fun newInstance() = InventoryBarcodeInputFragment()
    }

}