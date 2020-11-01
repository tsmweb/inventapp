package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.tsmweb.inventapp.common.Constants.EXTRA_BARCODE
import br.com.tsmweb.inventapp.common.Constants.EXTRA_INVENTORY_ITEM
import br.com.tsmweb.inventapp.databinding.FragmentInventoryPatrimonyInfoBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryItemBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InventoryPatrimonyInfoFragment: BottomSheetDialogFragment() {

    private val TAG = InventoryPatrimonyInfoFragment::class.simpleName

    private lateinit var binding: FragmentInventoryPatrimonyInfoBinding

    private val barcode: String? by lazy {
        arguments?.getString(EXTRA_BARCODE)
    }

    private val inventoryItemBinding: InventoryItemBinding? by lazy {
        arguments?.getParcelable<InventoryItemBinding>(EXTRA_INVENTORY_ITEM)
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

        binding.btnSave.setOnClickListener {
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

        fun newInstance(inventoryItemBinding: InventoryItemBinding): InventoryPatrimonyInfoFragment {
            return InventoryPatrimonyInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_INVENTORY_ITEM, inventoryItemBinding)
                }
            }
        }
    }

}