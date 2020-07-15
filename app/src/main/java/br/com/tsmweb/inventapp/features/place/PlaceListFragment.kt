package br.com.tsmweb.inventapp.features.place

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentPlaceListBinding
import br.com.tsmweb.inventapp.features.about.AboutDialogFragment
import br.com.tsmweb.inventapp.features.place.binding.PlaceBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceListFragment : BaseFragment(),
    MenuItem.OnActionExpandListener,
    SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentPlaceListBinding

    private val placeAdapter: PlaceAdapter by lazy {
        PlaceAdapter(this::onClick, this::onMenuItemClick)
    }

    private val viewModel: PlaceListViewModel by viewModel()

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place_list, container, false)

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
        inflater.inflate(R.menu.list_place_menu, menu)

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
        binding.rvPlaces.run {
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = placeAdapter
        }
    }

    private fun initFab() {
        binding.fabAddPlace.setOnClickListener {
            PlaceFormFragment.newInstance(PlaceBinding())
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

    private fun handleLoadState(state: ViewState<List<PlaceBinding>>) {
        when (state.status) {
            ViewState.Status.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ViewState.Status.SUCCESS -> {
                placeAdapter.setData(state.data)
                binding.progressBar.visibility = View.GONE
            }
            ViewState.Status.ERROR -> {
                binding.progressBar.visibility = View.GONE
                Snackbar.make(
                    binding.rvPlaces,
                    R.string.message_error_load_places,
                    Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleRemoveState(state: ViewState<Unit>) {
        when (state.status) {
            ViewState.Status.SUCCESS -> {
                Snackbar.make(
                    binding.rvPlaces,
                    R.string.message_success_remove_place,
                    Snackbar.LENGTH_LONG).show()
            }
            ViewState.Status.ERROR -> {
                Snackbar.make(
                    binding.rvPlaces,
                    R.string.message_error_remove_place,
                    Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun onClick(place: PlaceBinding) {
        Toast.makeText(requireContext(), place.name, Toast.LENGTH_SHORT).show()
    }

    private fun onMenuItemClick(item: MenuItem, place: PlaceBinding): Boolean {
        when (item.itemId) {
            R.id.menu_place_edit -> {
                PlaceFormFragment.newInstance(place).open(parentFragmentManager)
                return true
            }
            R.id.menu_place_remove -> {
                AlertDialog.Builder(requireContext())
                    .setMessage(R.string.message_confirm_remove_place)
                    .setPositiveButton(R.string.remove) { _, i ->
                        viewModel.removePlace(place)
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