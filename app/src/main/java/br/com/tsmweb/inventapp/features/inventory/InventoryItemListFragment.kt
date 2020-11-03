package br.com.tsmweb.inventapp.features.inventory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
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

class InventoryItemListFragment : BaseFragment(),
    MenuItem.OnActionExpandListener,
    SearchView.OnQueryTextListener {

    private val TAG = InventoryItemListFragment::class.simpleName

    private lateinit var binding: FragmentInventoryItemListBinding

    private var actionMode: ActionMode? = null
    private var searchView: SearchView? = null
    private var firstSearch: Boolean = true

    private var shareActionProvider: ShareActionProvider? = null

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
        InventoryItemAdapter(this::onClick, this::onLongClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventoryItemListBinding.inflate(inflater, container, false)

        initRecyclerView()
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriberViewModalObservable()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_inventory_item_menu, menu)

        // configure SearchView
        val searchItem = menu.findItem(R.id.action_search_inventory_item)
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
                InventoryPatrimonyInfoFragment
                    .newInstance(it)
                    .show(parentFragmentManager, "bottomSheetTag")
            }
        })

        viewModel.shareState().observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                handleShareState(it)
            }
        })

        viewModel.isInSelectionModel().observe(viewLifecycleOwner, Observer { selectionMode ->
            if (selectionMode) {
                showSelectionMode()
            } else {
                hideSelectionMode()
            }
        })

        viewModel.selectionCount().observe(viewLifecycleOwner, Observer { count ->
            count?.let {
                updateSelectionCountText(it)
                inventoryItemAdapter.notifyDataSetChanged()
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

                Log.e(TAG, state.error?.message ?: "")
            }
        }
    }

    private fun handleShareState(state: ViewState<List<InventoryItemBinding>>) {
        when (state.status) {
            ViewState.Status.SUCCESS -> {
                var content = StringBuilder()

                state.data?.forEach {
                    val item = getString(R.string.content_share_inventory_item, it.patrimonyCode, it.patrimonyName, it.note)
                    content.append(item)
                }

                Toast.makeText(requireContext(), content.toString(), Toast.LENGTH_LONG).show()

                shareActionProvider?.setShareIntent(Intent(Intent.ACTION_SEND).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, content.toString())
                })
            }
        }
    }

    private fun updateSelectionCountText(count: Int) {
        view?.post {
            actionMode?.title = resources.getQuantityString(R.plurals.list_inventory_selected, count, count)
        }
    }

    private fun showSelectionMode() {
        val appCompatActivity = (activity as AppCompatActivity)
        actionMode = appCompatActivity.startSupportActionMode(getActionModeCallBack())
    }

    private fun hideSelectionMode() {
        binding.rvInventoryItem.post {
            actionMode?.finish()
        }
    }

    private fun onClick(inventoryItem: InventoryItemBinding) {
        viewModel.selectInventoryItem(inventoryItem)
    }

    private fun onLongClick(inventoryItem: InventoryItemBinding): Boolean {
        if (actionMode == null) {
            viewModel.setInSelectionMode(true)
            viewModel.selectInventoryItem(inventoryItem)
            return true
        }

        return false
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

    private fun getActionModeCallBack() : ActionMode.Callback {
        return object : ActionMode.Callback {

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.action_share -> {
                        viewModel.shareSelected()
                        return true
                    }
                }

                return false
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                activity?.menuInflater?.inflate(R.menu.inventory_item_cab, menu)

                val shareItem = menu?.findItem(R.id.action_share)
                shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as? ShareActionProvider

                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

            override fun onDestroyActionMode(mode: ActionMode?) {
                actionMode = null
                viewModel.setInSelectionMode(false)
            }

        }
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