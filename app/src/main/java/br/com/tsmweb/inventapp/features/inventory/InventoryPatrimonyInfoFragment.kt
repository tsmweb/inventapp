package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.databinding.FragmentInventoryPatrimonyInfoBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InventoryPatrimonyInfoFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentInventoryPatrimonyInfoBinding

    private val bottomSheetPeekHeight: Int by lazy {
        resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_height)
    }

    private val barcode: String? by lazy {
        arguments?.getString(EXTRA_BARCODE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventoryPatrimonyInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtName.text = "GENUFLEXÓRIO BANCO DE MADEIRA (2,25 X 0.55 X 0,86 M)"
        binding.txtCode.text = barcode

        binding.btnDone.setOnClickListener {
            Toast.makeText(requireContext(), "Salvo", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        setUpBottomSheet()
    }

    private fun setUpBottomSheet() {
        BottomSheetBehavior.from(view?.parent as View).apply {
            peekHeight = bottomSheetPeekHeight
            isHideable = false
        }
    }

    companion object {
        private const val EXTRA_BARCODE = "barcode"

        fun newInstance(barcode: String): InventoryPatrimonyInfoFragment {
            return InventoryPatrimonyInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_BARCODE, barcode)
                }
            }
        }
    }

}