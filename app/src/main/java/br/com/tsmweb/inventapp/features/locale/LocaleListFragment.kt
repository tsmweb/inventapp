package br.com.tsmweb.inventapp.features.locale

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DefaultItemAnimator
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentLocaleListBinding
import br.com.tsmweb.inventapp.features.about.AboutDialogFragment
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class LocaleListFragment : BaseFragment(),
    MenuItem.OnActionExpandListener,
    SearchView.OnQueryTextListener {

    private val TAG = LocaleListFragment::class.simpleName

    private lateinit var binding: FragmentLocaleListBinding

    private val localeAdapter: LocaleAdapter by lazy {
        LocaleAdapter(this::onClick, this::onMenuItemClick)
    }

    private val viewModel: LocaleListViewModel by viewModel()

    private var searchView: SearchView? = null
    private var firstSearch: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_locale_list, container, false)

        initRecyclerView()
        initFab()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarConfiguration = AppBarConfiguration.Builder(router.getRootScreen()).build()
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(binding.toolbar, navHostFragment, appBarConfiguration)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        subscriberViewModalObservable()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_locale_menu, menu)

        val searchItem = menu.findItem(R.id.action_search_locale)
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
            R.id.action_about ->
                AboutDialogFragment().show(parentFragmentManager, "about")
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
            viewModel.search(query ?: "")
        }

        return true
    }

    private fun initRecyclerView() {
        binding.rvLocales.run {
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = localeAdapter
        }
    }

    private fun initFab() {
        binding.fabAddLocale.setOnClickListener {
            LocaleFormFragment.newInstance(LocaleBinding())
                .open(parentFragmentManager)
        }
    }

    private fun subscriberViewModalObservable() {
        viewModel.loadState().observe(viewLifecycleOwner, Observer { state ->
            if (state != null) {
                handleLoadState(state)
            }
        })

        viewModel.removeState().observe(viewLifecycleOwner, Observer { state ->
            if (state != null) {
                handleRemoveState(state)
            }
        })

        if (viewModel.loadState().value == null) {
            viewModel.search()
        }
    }

    private fun handleLoadState(state: ViewState<List<LocaleBinding>>) {
        when (state.status) {
            ViewState.Status.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ViewState.Status.SUCCESS -> {
                localeAdapter.setData(state.data)
                binding.progressBar.visibility = View.GONE
            }
            ViewState.Status.ERROR -> {
                binding.progressBar.visibility = View.GONE
                Snackbar.make(
                    binding.rvLocales,
                    R.string.locale_message_error_load,
                    Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleRemoveState(state: ViewState<Unit>) {
        when (state.status) {
            ViewState.Status.SUCCESS -> {
                Snackbar.make(
                    binding.rvLocales,
                    R.string.locale_message_success_remove,
                    Snackbar.LENGTH_LONG).show()
            }
            ViewState.Status.ERROR -> {
                Snackbar.make(
                    binding.rvLocales,
                    R.string.locale_message_error_remove,
                    Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun onClick(locale: LocaleBinding) {
        router.showLocaleTabs(locale)
    }

    private fun onMenuItemClick(item: MenuItem, locale: LocaleBinding): Boolean {
        when (item.itemId) {
            R.id.menu_locale_edit -> {
                LocaleFormFragment.newInstance(locale).open(parentFragmentManager)
                return true
            }
            R.id.menu_locale_remove -> {
                AlertDialog.Builder(requireContext())
                    .setMessage(R.string.locale_message_confirm_remove)
                    .setPositiveButton(R.string.remove) { _, i ->
                        viewModel.removePlace(locale)
                    }
                    .setNegativeButton(R.string.cancel, null)
                    .create()
                    .show()

                return true
            }
        }

        return false
    }

}