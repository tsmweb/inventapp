package br.com.tsmweb.inventapp.features.patrimony

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentPatrimonyListBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PatrimonyListFragment : BaseFragment(),
    MenuItem.OnActionExpandListener,
    SearchView.OnQueryTextListener {

    private val TAG = PatrimonyListFragment::class.simpleName

    private lateinit var binding: FragmentPatrimonyListBinding
    private var actionMode: ActionMode? = null

    private val locale: LocaleBinding? by lazy {
        arguments?.getParcelable<LocaleBinding>(EXTRA_LOCALE)
    }

    private val patrimonyAdapter: PatrimonyAdapter by lazy {
        PatrimonyAdapter(this::onClick, this::onLongClick)
    }

    private val viewModel: PatrimonyListViewModel by viewModel {
        parametersOf(locale?.id)
    }

    private var searchView: SearchView? = null
    private var firstSearch: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatrimonyListBinding.inflate(inflater, container, false)

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
        inflater.inflate(R.menu.list_patrimony_menu, menu)

        val searchItem = menu.findItem(R.id.action_search_patrimony)
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
        binding.rvPatrimonies.run {
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
//            setHasFixedSize(true)
            adapter = patrimonyAdapter
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

        viewModel.showDetails().observe(viewLifecycleOwner, Observer { patrimony ->
            patrimony?.let {
                router.showPatrimonyDetails(it)
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
                patrimonyAdapter.notifyDataSetChanged()
            }
        })

        if (viewModel.loadState().value == null) {
            viewModel.search()
        }
    }

    private fun handleLoadState(state: ViewState<List<PatrimonyBinding>>) {
        when (state.status) {
            ViewState.Status.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ViewState.Status.SUCCESS -> {
                patrimonyAdapter.setData(state.data)
                binding.progressBar.visibility = View.GONE
            }
            ViewState.Status.ERROR -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    R.string.patrimony_message_error_load,
                    Toast.LENGTH_SHORT
                ).show()

                Log.e(TAG, state.error?.message ?: "")
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
                    Toast.LENGTH_LONG
                ).show()
            }
            ViewState.Status.ERROR -> {
                Toast.makeText(requireContext(),
                    R.string.message_error_remove_patrimonies, Toast.LENGTH_SHORT).show()

                Log.e(TAG, state.error?.message ?: "")
            }
        }
    }

    private fun updateSelectionCountText(count: Int) {
        view?.post {
            actionMode?.title = resources.getQuantityString(R.plurals.list_patrimony_selected, count, count)
        }
    }

    private fun showDeleteMode() {
        val appCompatActivity = (activity as AppCompatActivity)
        actionMode = appCompatActivity.startSupportActionMode(getActionModeCallBack())
    }

    private fun hideDeleteMode() {
        binding.rvPatrimonies.post {
            actionMode?.finish()
        }
    }

    private fun onClick(patrimony: PatrimonyBinding) {
        viewModel.selectPatrimony(patrimony)
    }

    private fun onLongClick(patrimony: PatrimonyBinding): Boolean {
        if (actionMode == null) {
            viewModel.setInDeleteMode(true)
            viewModel.selectPatrimony(patrimony)
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

        fun newInstance(locale: LocaleBinding) = PatrimonyListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_LOCALE, locale)
            }
        }
    }
}