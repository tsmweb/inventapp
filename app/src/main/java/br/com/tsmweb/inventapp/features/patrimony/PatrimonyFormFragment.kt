package br.com.tsmweb.inventapp.features.patrimony

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
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
import br.com.tsmweb.inventapp.databinding.FragmentPatrimonyFormBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PatrimonyFormFragment : BaseFragment() {

    private val TAG = PatrimonyFormFragment::class.simpleName

    private lateinit var binding: FragmentPatrimonyFormBinding

    private val patrimony: PatrimonyBinding? by lazy {
        arguments?.getParcelable<PatrimonyBinding>(EXTRA_PATRIMONY)
    }

    private val viewModel: PatrimonyFormViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatrimonyFormBinding.inflate(inflater, container, false)
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

        patrimony?.let {
            viewModel.setPatrimony(it)
            binding.patrimony = it
        }

        binding.edtName.setOnEditorActionListener{ _, i, _ ->
            handleKeyboardEvent(i)
        }

        subscriberViewModalObservable()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.form_patrimony_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_patrimony_save -> savePatrimony()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun handleKeyboardEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            savePatrimony()
            return true
        }

        return false
    }

    private fun subscriberViewModalObservable() {
        viewModel.saveState().observe(viewLifecycleOwner, Observer { state ->
            state?.run {
                when (status) {
                    ViewState.Status.LOADING -> {
                        Log.d(TAG, "Process is loading")
                    }
                    ViewState.Status.SUCCESS -> {
                        router.back()
                    }
                    ViewState.Status.ERROR -> {
                        Log.d(TAG, error.toString())
                        Toast.makeText(
                            requireContext(),
                            R.string.message_error_save_patrimony,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun savePatrimony() {
        val patrimony = binding.patrimony!!

        if (patrimony.code.isBlank()) {
            binding.edtCode.error = getString(R.string.message_form_enter_code)
            binding.edtCode.requestFocus()
            return
        }

        if (patrimony.name.isBlank()) {
            binding.edtName.error = getString(R.string.message_form_enter_name)
            binding.edtName.requestFocus()
            return
        }

        if (patrimony.dependency.isBlank()) {
            binding.edtDependency.error = getString(R.string.message_form_enter_dependency)
            binding.edtDependency.requestFocus()
            return
        }

        viewModel.savePatrimony()
    }

}