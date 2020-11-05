package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.Constants.EXTRA_BARCODE
import br.com.tsmweb.inventapp.common.Constants.EXTRA_INVENTORY_ID
import br.com.tsmweb.inventapp.common.Constants.EXTRA_INVENTORY_ITEM
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentInventoryPatrimonyInfoBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryItemBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InventoryPatrimonyInfoFragment: BottomSheetDialogFragment() {

    private val TAG = InventoryPatrimonyInfoFragment::class.simpleName

    private lateinit var binding: FragmentInventoryPatrimonyInfoBinding

    private val inventoryId: Long by lazy {
        arguments?.getLong(EXTRA_INVENTORY_ID) ?: 0
    }

    private val barcode: String by lazy {
        arguments?.getString(EXTRA_BARCODE) ?: ""
    }

//    private val inventoryItemBinding: InventoryItemBinding? by lazy {
//        arguments?.getParcelable<InventoryItemBinding>(EXTRA_INVENTORY_ITEM)
//    }

    private val viewModel: InventoryPatrimonyInfoViewModel by viewModel()

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

//        binding.inventoryItem = inventoryItemBinding

        binding.btnSave.setOnClickListener {
            viewModel.saveInventoryItem()
        }

        subscriberViewModalObservable()

        viewModel.findInventoryItem(inventoryId, barcode)
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

    private fun subscriberViewModalObservable() {
        viewModel.findState().observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                handleFindState(state)
            }
        })

        viewModel.saveState().observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                handleSaveState(state)
            }
        })
    }

    private fun handleFindState(state: ViewState<InventoryItemBinding>) {
        when (state.status) {
            ViewState.Status.LOADING -> {}
            ViewState.Status.SUCCESS -> {
                binding.inventoryItem = state.data
            }
            ViewState.Status.ERROR -> {
                Toast.makeText(requireContext(),
                    R.string.message_error_find_inventory_item,
                    Toast.LENGTH_SHORT).show()

                Log.e(TAG, state.error?.message ?: "")
            }
        }
    }

    private fun handleSaveState(state: ViewState<Unit>) {
        when (state.status) {
            ViewState.Status.LOADING -> {}
            ViewState.Status.SUCCESS -> {
                dismiss()
            }
            ViewState.Status.ERROR -> {
                Toast.makeText(requireContext(),
                    R.string.message_error_save_inventory_item,
                    Toast.LENGTH_SHORT).show()

                Log.e(TAG, state.error?.message ?: "")
            }
        }
    }

    companion object {
        fun newInstance(inventoryId: Long, barcode: String): InventoryPatrimonyInfoFragment {
            return InventoryPatrimonyInfoFragment().apply {
                arguments = Bundle().apply {
                    putLong(EXTRA_INVENTORY_ID, inventoryId)
                    putString(EXTRA_BARCODE, barcode)
                }
            }
        }

//        fun newInstance(inventoryItemBinding: InventoryItemBinding): InventoryPatrimonyInfoFragment {
//            return InventoryPatrimonyInfoFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable(EXTRA_INVENTORY_ITEM, inventoryItemBinding)
//                }
//            }
//        }
    }

}