package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.view.*
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentInventoryListBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InventoryListFragment : BaseFragment(),
    MenuItem.OnActionExpandListener,
    SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentInventoryListBinding
    private val viewModel: InventoryListViewModel by viewModel {
        parametersOf(arguments?.getParcelable<LocaleBinding>(EXTRA_LOCALE)?.id)
    }

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
        binding = FragmentInventoryListBinding.inflate(inflater, container, false)

        initRecyclerView()

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

    private fun subscriberViewModalObservable() {
        viewModel.loadState().observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                handleLoadState(it)
            }
        })

        viewModel.deleteState().observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                handleDeleteState(it)
            }
        })

        viewModel.showDetails().observe(viewLifecycleOwner, Observer { inventory ->
            inventory?.let {
                Toast.makeText(requireContext(), it.locale?.code, Toast.LENGTH_SHORT).show()
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
            count?.let {
                updateSelectionCountText(it)
                inventoryAdapter.notifyDataSetChanged()
            }
        })

        if (viewModel.loadState().value == null) {
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
                Toast.makeText(
                    requireContext(),
                    R.string.message_error_load_inventory,
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleDeleteState(state: ViewState<Int>) {
        when (state.status) {
            ViewState.Status.SUCCESS -> {
                val count = state.data ?: 0
                Toast.makeText(
                    requireContext(),
                    resources.getQuantityString(R.plurals.message_item_deleted, count, count),
                    Toast.LENGTH_SHORT).show()
            }
            ViewState.Status.ERROR -> {
                Toast.makeText(
                    requireContext(),
                    R.string.message_error_remove_inventory,
                    Toast.LENGTH_SHORT).show()
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
        actionMode?.let {
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

    companion object {
        const val EXTRA_LOCALE = "locale"

        fun newInstance(locale: LocaleBinding) = InventoryListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_LOCALE, locale)
            }
        }
    }

}