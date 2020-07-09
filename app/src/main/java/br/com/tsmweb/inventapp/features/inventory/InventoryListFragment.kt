package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.view.*
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.presentation.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentInventoryListBinding
import br.com.tsmweb.presentation.inventory.binding.InventoryBinding
import br.com.tsmweb.presentation.inventory.InventoryListViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class InventoryListFragment : BaseFragment(),
    MenuItem.OnActionExpandListener,
    SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentInventoryListBinding
    private val viewModel: InventoryListViewModel by viewModel()

    private var actionMode: ActionMode? = null
    private var searchView: SearchView? = null
    private var firstSearch: Boolean = true

    private val inventoryAdapter by lazy {
        InventoryAdapter(this::onClick, this::onLongClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inventory_list, container, false)

        initRecyclerView()
        initFab()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriberViewModalObservable()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.inventories_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchItem.setOnActionExpandListener(this)

        searchView = searchItem.actionView as SearchView
        searchView?.maxWidth = Int.MAX_VALUE
        searchView?.queryHint = getString(R.string.hint_search)
        searchView?.setOnQueryTextListener(this)

        if (viewModel.getLastSearchTerm().isNotEmpty()) {
            searchView?.post {
                val query = viewModel.getLastSearchTerm()
                searchItem.expandActionView()
                searchView?.setQuery(query, true)
                searchView?.clearFocus()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort_date_desc -> {
                //TODO
                return true
            }
            R.id.action_sort_date_asc -> {
                //TODO
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onMenuItemActionExpand(item: MenuItem?) = true

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        viewModel.search()
        return true
    }

    override fun onQueryTextSubmit(query: String?) = true

    override fun onQueryTextChange(query: String?): Boolean {
        if (firstSearch) {
            firstSearch = false
        } else {
            Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
            viewModel.search(query ?: "")
        }

        return true
    }

    private fun initRecyclerView() {
        binding.rvInventories.run {
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
            adapter = inventoryAdapter
        }
    }

    private fun initFab() {
        binding.fabAddInventory.setOnClickListener {
            Toast.makeText(requireContext(), "Novo Inventário", Toast.LENGTH_SHORT).show()
        }
    }

    private fun subscriberViewModalObservable() {
        viewModel.loadState().observe(viewLifecycleOwner, Observer { state ->
            if (state != null) {
                handleLoadState(state)
            }
        })

        viewModel.deleteState().observe(viewLifecycleOwner, Observer { state ->
            if (state != null) {
                handleDeleteState(state)
            }
        })

        viewModel.showDetails().observe(viewLifecycleOwner, Observer { inventory ->
            if (inventory != null) {
                Toast.makeText(requireContext(), inventory.place?.code, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.isInDeleteModel().observe(viewLifecycleOwner, Observer { deleteMode ->
            if (deleteMode) {
                showDeleteMode()
            } else {
                hideDeleteMode()
            }
        })

        viewModel.selectionCount().observe(viewLifecycleOwner, Observer { count ->
            if (count != null) {
                updateSelectionCountText(count)
                inventoryAdapter.notifyDataSetChanged()
            }
        })

        if (viewModel.loadState().value == null) {
            viewModel.populateInitialData()
            viewModel.search()
        }
    }

    private fun handleLoadState(state: ViewState<List<InventoryBinding>>) {
        when (state.status) {
            ViewState.Status.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ViewState.Status.SUCCESS -> {
                inventoryAdapter.setData(state.data)
                binding.progressBar.visibility = View.GONE
            }
            ViewState.Status.ERROR -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),
                    R.string.message_error_load_inventories, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleDeleteState(state: ViewState<Int>) {
        when (state.status) {
            ViewState.Status.SUCCESS -> {
                val count = state.data ?: 0
                Snackbar.make(binding.rvInventories,
                    resources.getQuantityString(R.plurals.message_item_deleted, count, count),
                    Snackbar.LENGTH_LONG).show()
            }
            ViewState.Status.ERROR -> {
                Toast.makeText(requireContext(),
                    R.string.message_error_delete_inventory, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateSelectionCountText(count: Int) {
        view?.post {
            actionMode?.title = resources.getQuantityString(R.plurals.list_inventory_selected, count, count)
        }
    }

    private fun showDeleteMode() {
        val appCompatActivity = (activity as AppCompatActivity)
        actionMode = appCompatActivity.startSupportActionMode(getActionModeCallBack())
    }

    private fun hideDeleteMode() {
        binding.rvInventories.post {
            actionMode?.finish()
        }
    }

    private fun onClick(inventoryBinding: InventoryBinding) {
        viewModel.selectInventory(inventoryBinding)
    }

    private fun onLongClick(inventoryBinding: InventoryBinding): Boolean {
        if (actionMode == null) {
            viewModel.setInDeleteMode(true)
            viewModel.selectInventory(inventoryBinding)
            return true
        }

        return false
    }

    private fun getActionModeCallBack() : ActionMode.Callback {
        return object : ActionMode.Callback {

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                if (item?.itemId == R.id.action_delete) {
                    viewModel.deleteSelected()
                    return true
                }

                return false
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                activity?.menuInflater?.inflate(R.menu.inventories_cab, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

            override fun onDestroyActionMode(mode: ActionMode?) {
                actionMode = null
                viewModel.setInDeleteMode(false)
            }

        }
    }

}