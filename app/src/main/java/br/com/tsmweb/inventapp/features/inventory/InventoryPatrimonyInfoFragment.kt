package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentInventoryPatrimonyInfoBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryItemBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class InventoryPatrimonyInfoFragment: BottomSheetDialogFragment() {

    private val TAG = InventoryPatrimonyInfoFragment::class.simpleName

    private lateinit var binding: FragmentInventoryPatrimonyInfoBinding

    private val inventoryId: Long by lazy {
        arguments?.getLong(EXTRA_INVENTORY_ID) ?: 0
    }

    private val barcode: String by lazy {
        arguments?.getString(EXTRA_BARCODE) ?: ""
    }

    private val editMode: Boolean by lazy {
        arguments?.getBoolean(EXTRA_EDIT_MODE) ?: true
    }

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

        subscriberViewModalObservable()

        binding.btnSave.setOnClickListener {
            viewModel.saveInventoryItem(editMode)
        }

        binding.edtNote.setOnEditorActionListener{ _, i, _ ->
            handleKeyboardEvent(i)
        }

        viewModel.findInventoryItem(inventoryId, barcode)
    }

    private fun handleKeyboardEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            viewModel.saveInventoryItem(editMode)
            return true
        }

        return false
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
        viewModel.findState().observe(viewLifecycleOwner, { state ->
            state?.let {
                handleFindState(state)
            }
        })

        viewModel.saveState().observe(viewLifecycleOwner, { state ->
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

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(PATRIMONY_INFO_DIALOG_TAG) == null) {
            show(fm, PATRIMONY_INFO_DIALOG_TAG)
        }
    }

    companion object {
        private const val PATRIMONY_INFO_DIALOG_TAG = "inventoryPatrimonyInfoDialog"
        private const val EXTRA_INVENTORY_ID = "inventory_id"
        private const val EXTRA_BARCODE = "barcode"
        private const val EXTRA_EDIT_MODE = "edit_mode"

        fun newInstance(inventoryId: Long, barcode: String, editMode: Boolean = true): InventoryPatrimonyInfoFragment {
            return InventoryPatrimonyInfoFragment().apply {
                arguments = Bundle().apply {
                    putLong(EXTRA_INVENTORY_ID, inventoryId)
                    putString(EXTRA_BARCODE, barcode)
                    putBoolean(EXTRA_EDIT_MODE, editMode)
                }
            }
        }
    }

}