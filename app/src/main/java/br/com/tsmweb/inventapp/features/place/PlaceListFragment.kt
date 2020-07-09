package br.com.tsmweb.inventapp.features.place

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentPlaceListBinding
import br.com.tsmweb.inventapp.features.place.binding.PlaceBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceListFragment : BaseFragment(),
    MenuItem.OnActionExpandListener,
    SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentPlaceListBinding

    private val placeAdapter: PlaceAdapter by lazy {
        PlaceAdapter(this::onClick)
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
        binding.rvPlaces.run {
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = placeAdapter
        }
    }

    private fun initFab() {
        binding.fabAddPlace.setOnClickListener {
            Toast.makeText(requireContext(), "Nova Localidade", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(requireContext(),
                    R.string.message_error_load_places, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onClick(place: PlaceBinding) {
        Toast.makeText(requireContext(), place.name, Toast.LENGTH_SHORT).show()
    }

}