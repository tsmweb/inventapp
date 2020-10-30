package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.Constants.EXTRA_ACTIVE_PEEK
import br.com.tsmweb.inventapp.common.Constants.EXTRA_BARCODE
import br.com.tsmweb.inventapp.common.Constants.EXTRA_INVENTORY_ITEM
import br.com.tsmweb.inventapp.databinding.FragmentInventoryPatrimonyInfoBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryItemBinding
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

    private val inventoryItemBinding: InventoryItemBinding? by lazy {
        arguments?.getParcelable<InventoryItemBinding>(EXTRA_INVENTORY_ITEM)
    }

    private val activatePeek: Boolean? by lazy {
        arguments?.getBoolean(EXTRA_ACTIVE_PEEK, true)
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

        binding.inventoryItem = inventoryItemBinding
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
            if (activatePeek == true) {
                peekHeight = bottomSheetPeekHeight
            }
            isHideable = false
        }
    }

    companion object {
        fun newInstance(barcode: String): InventoryPatrimonyInfoFragment {
            return InventoryPatrimonyInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_BARCODE, barcode)
                }
            }
        }

        fun newInstance(inventoryItemBinding: InventoryItemBinding, activePeek: Boolean = true): InventoryPatrimonyInfoFragment {
            return InventoryPatrimonyInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_INVENTORY_ITEM, inventoryItemBinding)
                    putBoolean(EXTRA_ACTIVE_PEEK, activePeek)
                }
            }
        }
    }

}