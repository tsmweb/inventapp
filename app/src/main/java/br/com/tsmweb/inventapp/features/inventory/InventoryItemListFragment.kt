package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.Constants.EXTRA_INVENTORY
import br.com.tsmweb.inventapp.common.Constants.EXTRA_PATRIMONY_STATE
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentInventoryItemListBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryItemBinding
import br.com.tsmweb.inventapp.features.inventory.binding.StatusInventory
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InventoryItemListFragment : BaseFragment() {

    private lateinit var binding: FragmentInventoryItemListBinding

    private val inventory: InventoryBinding? by lazy {
        arguments?.getParcelable<InventoryBinding>(EXTRA_INVENTORY)
    }

    private val statusInventory: StatusInventory? by lazy {
        arguments?.getString(EXTRA_PATRIMONY_STATE)?.let {
            StatusInventory.valueOf(it)
        }
    }

    private val viewModel: InventoryItemListViewModel by viewModel {
        parametersOf(
            inventory?.id,
            statusInventory
        )
    }

    private val inventoryItemAdapter by lazy {
        InventoryItemAdapter(this::onClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventoryItemListBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriberViewModalObservable()
    }

    private fun initRecyclerView() {
        binding.rvInventoryItem.run {
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
            adapter = inventoryItemAdapter
        }
    }

    private fun subscriberViewModalObservable() {
        viewModel.loadState().observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                handleLoadState(it)
            }
        })

        viewModel.showDetails().observe(viewLifecycleOwner, Observer { item ->
            item?.let {
                Toast.makeText(requireContext(), it.patrimony?.code, Toast.LENGTH_SHORT).show()
            }
        })

        if (viewModel.loadState().value == null) {
            viewModel.search()
        }
    }

    private fun handleLoadState(state: ViewState<List<InventoryItemBinding>>) {
        when (state.status) {
            ViewState.Status.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ViewState.Status.SUCCESS -> {
                inventoryItemAdapter.setData(state.data)
                binding.progressBar.visibility = View.GONE
            }
            ViewState.Status.ERROR -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    R.string.message_error_load_inventory_item,
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onClick(inventoryItem: InventoryItemBinding) {
        viewModel.selectInventory(inventoryItem)
    }

    companion object {

        fun newInstance(inventory: InventoryBinding, statusInventory: StatusInventory) = InventoryItemListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_INVENTORY, inventory)
                putString(EXTRA_PATRIMONY_STATE, statusInventory.name)
            }
        }
    }

}