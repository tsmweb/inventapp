package br.com.tsmweb.inventapp.features.patrimony

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentPatrimonyListBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class PatrimonyListFragment : BaseFragment(),
    MenuItem.OnActionExpandListener,
    SearchView.OnQueryTextListener {

    private val TAG = PatrimonyListFragment::class.simpleName

    private lateinit var binding: FragmentPatrimonyListBinding

    private val locale: LocaleBinding? by lazy {
        arguments?.getParcelable<LocaleBinding>(EXTRA_LOCALE)
    }

    private val patrimonyAdapter: PatrimonyAdapter by lazy {
        PatrimonyAdapter(this::onClick)
    }

    private val viewModel: PatrimonyListViewModel by inject {
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

    private fun onClick(patrimony: PatrimonyBinding) {
        PatrimonyFormFragment.newInstance(patrimony).open(parentFragmentManager)
    }

    private fun initRecyclerView() {
        binding.rvPatrimonies.run {
            itemAnimator = DefaultItemAnimator()
//            setHasFixedSize(true)
            adapter = patrimonyAdapter
        }
    }

    private fun subscriberViewModalObservable() {
        viewModel.loadState().observe(viewLifecycleOwner, Observer { state ->
            if (state != null) {
                handleLoadState(state)
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
                Snackbar.make(
                    binding.rvPatrimonies,
                    R.string.patrimony_message_error_load,
                    Snackbar.LENGTH_SHORT).show()
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