package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.Constants.EXTRA_INVENTORY
import br.com.tsmweb.inventapp.common.Constants.EXTRA_PATRIMONY_STATE
import br.com.tsmweb.inventapp.databinding.FragmentInventoryItemListBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryBinding

class InventoryItemListFragment : BaseFragment() {

    private lateinit var binding: FragmentInventoryItemListBinding

    private val inventory: InventoryBinding? by lazy {
        arguments?.getParcelable<InventoryBinding>(EXTRA_INVENTORY)
    }

    private val patrimonyState: Int by lazy {
        arguments?.getInt(EXTRA_PATRIMONY_STATE, 0) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventoryItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtPatrimonyState.text = getString(patrimonyState)
    }

    companion object {

        fun newInstance(inventory: InventoryBinding, patrimonyState: Int) = InventoryItemListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_INVENTORY, inventory)
                putInt(EXTRA_PATRIMONY_STATE, patrimonyState)
            }
        }
    }

}