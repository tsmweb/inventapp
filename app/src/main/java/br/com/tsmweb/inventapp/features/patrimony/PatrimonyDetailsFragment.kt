package br.com.tsmweb.inventapp.features.patrimony

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.Constants.EXTRA_PATRIMONY
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentPatrimonyDetailsBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PatrimonyDetailsFragment : BaseFragment() {

    private val TAG = PatrimonyDetailsFragment::class.simpleName

    private lateinit var binding: FragmentPatrimonyDetailsBinding

    private val patrimony: PatrimonyBinding? by lazy {
        arguments?.getParcelable<PatrimonyBinding>(EXTRA_PATRIMONY)
    }

    private val viewModel: PatrimonyDetailsViewModel by viewModel {
        parametersOf(patrimony?.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatrimonyDetailsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(binding.toolbar, navHostFragment)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        subscriberViewModalObservable()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_patrimony_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit_patrimony -> {
                val pat = patrimony ?: PatrimonyBinding()
                PatrimonyFormFragment.newInstance(pat).open(parentFragmentManager)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun subscriberViewModalObservable() {
        viewModel.loadState().observe(viewLifecycleOwner, Observer { state ->
            state?.run {
                when (status) {
                    ViewState.Status.SUCCESS -> {
                        binding.patrimony = data
                    }
                    ViewState.Status.ERROR -> {
                        Log.d(TAG, error.toString())
                        Toast.makeText(
                            requireContext(),
                            R.string.message_error_load_patrimony,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })

        if (viewModel.loadState().value == null) {
            viewModel.loadPatrimony()
        }
    }

}