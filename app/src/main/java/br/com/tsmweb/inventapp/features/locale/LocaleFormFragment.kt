package br.com.tsmweb.inventapp.features.locale

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
import br.com.tsmweb.inventapp.common.Constants.EXTRA_LOCALE
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentLocaleFormBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LocaleFormFragment : BaseFragment() {

    private val TAG: String = LocaleFormFragment::class.java.simpleName

    private lateinit var binding: FragmentLocaleFormBinding

    private val locale: LocaleBinding? by lazy {
        arguments?.getParcelable<LocaleBinding>(EXTRA_LOCALE)
    }

    private val viewModel: LocaleFormViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocaleFormBinding.inflate(inflater, container, false)
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

        locale?.let {
            viewModel.setLocale(it)
            binding.locale = it
        }

        binding.edtName.setOnEditorActionListener{ _, i, _ ->
            handleKeyboardEvent(i)
        }

        subscriberViewModalObservable()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.form_locale_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_locale_save -> savePlace()
        }

        return super.onOptionsItemSelected(item)
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
                        Toast.makeText(requireContext(), R.string.message_error_save_locale, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun handleKeyboardEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            savePlace()
            return true
        }

        return false
    }

    private fun savePlace() {
        val locale = binding.locale!!

        if (locale.code.isBlank()) {
            binding.edtCode.error = getString(R.string.message_form_enter_code)
            binding.edtCode.requestFocus()
            return
        }

        if (locale.name.isBlank()) {
            binding.edtName.error = getString(R.string.message_form_enter_name)
            binding.edtName.requestFocus()
            return
        }

        viewModel.saveLocale()
    }

}