package br.com.tsmweb.inventapp.features.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.Constants.EXTRA_INVENTORY
import br.com.tsmweb.inventapp.common.extensions.toView
import br.com.tsmweb.inventapp.databinding.FragmentInventoryTabBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryBinding
import com.google.android.material.tabs.TabLayoutMediator

class InventoryTabFragment : BaseFragment() {

    private val TAG = InventoryTabFragment::class.simpleName

    private lateinit var binding: FragmentInventoryTabBinding
    private lateinit var tabsPagerAdapter: InventoryTabsPagerAdapter

    private val inventory: InventoryBinding? by lazy {
        arguments?.getParcelable<InventoryBinding>(EXTRA_INVENTORY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventoryTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(binding.toolbar, navHostFragment)

        binding.toolbar.apply {
            title = inventory?.dateInventory?.toView(resources.getString(R.string.date_format))
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        inventory?.let {
            initTabLayout(it)
            initFab(it)
        }
    }

    private fun initTabLayout(inventory: InventoryBinding) {
        val tabsPagerAdapter = InventoryTabsPagerAdapter(this).apply {
            addFragment(InventoryItemListFragment.newInstance(inventory, R.string.checked))
            addFragment(InventoryItemListFragment.newInstance(inventory, R.string.unchecked))
            addFragment(InventoryItemListFragment.newInstance(inventory, R.string.not_found))
        }

        binding.viewPagerInventory.adapter = tabsPagerAdapter

        TabLayoutMediator(binding.tabLayoutInventory, binding.viewPagerInventory) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.checked)
                1 -> tab.text = getString(R.string.unchecked)
                2 -> tab.text = getString(R.string.not_found)
            }
        }.attach()
    }

    private fun initFab(inventory: InventoryBinding) {
        binding.fab.setOnClickListener {
            router.showBarcodeReader(inventory)
        }
    }
}