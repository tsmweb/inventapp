package br.com.tsmweb.inventapp.features.patrimony

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.Constants.EXTRA_PATRIMONY
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.common.extensions.closeKeyboard
import br.com.tsmweb.inventapp.common.extensions.showKeyboard
import br.com.tsmweb.inventapp.databinding.FragmentPatrimonyFormBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PatrimonyFormFragment : BaseFragment() {

    private val TAG = PatrimonyFormFragment::class.simpleName

    private lateinit var binding: FragmentPatrimonyFormBinding

    private val patrimony: PatrimonyBinding? by lazy {
        arguments?.getParcelable(EXTRA_PATRIMONY)
    }

    private val viewModel: PatrimonyFormViewModel by viewModel()

    private val dependencyAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(
            requireContext(),
            R.layout.dropdown_menu_popup_item
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatrimonyFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(binding.toolbar, navHostFragment)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener { _view ->
            _view.findNavController().navigateUp()
        }

        patrimony?.let {
            viewModel.setPatrimony(it)
            binding.patrimony = it
        }

        binding.edtDependency.setAdapter(dependencyAdapter)

        binding.btnSave.setOnClickListener {
            savePatrimony()
        }

        binding.edtName.setOnEditorActionListener{ _, i, _ ->
            handleKeyboardEvent(i)
        }

        subscriberViewModalObservable()
    }

    override fun onResume() {
        super.onResume()

        binding.edtCode.post {
            binding.edtCode.requestFocus()
            showKeyboard()
        }
    }

    override fun onDestroyView() {
        closeKeyboard()
        super.onDestroyView()
    }

    private fun handleKeyboardEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            savePatrimony()
            return true
        }

        return false
    }

    private fun subscriberViewModalObservable() {
        viewModel.saveState().observe(viewLifecycleOwner) { state ->
            state?.let {
                handleSaveState(it)
            }
        }

        viewModel.loadDependencyState().observe(viewLifecycleOwner) { state ->
            state?.let {
                handleDependencyListState(it)
            }
        }

        patrimony?.locale?.let {
            viewModel.loadDependency(it.id)
        }
    }

    private fun handleSaveState(state: ViewState<Unit>) {
        state.run {
            when (status) {
                ViewState.Status.LOADING -> {
                    Log.d(TAG, "[SaveState] Process is loading")
                }
                ViewState.Status.SUCCESS -> {
                    router.back()
                }
                ViewState.Status.ERROR -> {
                    Log.e(TAG, state.error?.message ?: "")

                    Toast.makeText(
                        requireContext(),
                        R.string.message_error_save_patrimony,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun handleDependencyListState(state: ViewState<List<String>>) {
        state.run {
            when (status) {
                ViewState.Status.LOADING -> {
                    Log.d(TAG, "[DependencyListState] Process is loading")
                }
                ViewState.Status.SUCCESS -> {
                    data?.let {
                        dependencyAdapter.clear()
                        dependencyAdapter.addAll(it)
                    }
                }
                ViewState.Status.ERROR -> {
                    Log.e(TAG, state.error?.message ?: "")
                }
            }
        }

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