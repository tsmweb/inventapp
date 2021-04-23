package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.Constants.EXTRA_LOCALE
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.databinding.FragmentInventoryNewBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import kotlinx.android.synthetic.main.fragment_inventory_new.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InventoryNewFragment : DialogFragment() {

    private val TAG = InventoryNewFragment::class.simpleName

    private lateinit var binding: FragmentInventoryNewBinding

    private val viewModel: InventoryNewViewModel by viewModel {
        parametersOf(
            arguments?.getParcelable<LocaleBinding>(EXTRA_LOCALE)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventoryNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnCreate.setOnClickListener {
            viewModel.saveInventory()
        }

        subscriberViewModalObservable()

        dialog?.setTitle(R.string.title_new_inventory)
    }

    private fun subscriberViewModalObservable() {
        viewModel.saveState().observe(viewLifecycleOwner, { state ->
            state?.let {
                when (it.status) {
                    ViewState.Status.LOADING -> {
                        binding.contentLoading.visibility = View.VISIBLE
                        binding.contentNewInventory.visibility = View.GONE
                    }
                    ViewState.Status.SUCCESS -> {
                        dismiss()
                    }
                    ViewState.Status.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            R.string.message_error_new_inventory,
                            Toast.LENGTH_SHORT).show()

                        Log.e(TAG, state.error?.message ?: "")

                        dismiss()
                    }
                }
            }
        })
    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG)
        }
    }

    companion object {
        private const val DIALOG_TAG = "newInventoryDialog"

        fun newInstance(locale: LocaleBinding) = InventoryNewFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_LOCALE, locale)
            }
        }
    }

}