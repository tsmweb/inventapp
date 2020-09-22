package br.com.tsmweb.inventapp.router

import android.os.Bundle
import androidx.navigation.NavController
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.Constants.EXTRA_INVENTORY
import br.com.tsmweb.inventapp.common.Constants.EXTRA_LOCALE
import br.com.tsmweb.inventapp.common.Constants.EXTRA_PATRIMONY
import br.com.tsmweb.inventapp.common.Router
import br.com.tsmweb.inventapp.features.inventory.InventoryNewFragment
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding

class AppRouter(
    private val navController: NavController
): Router {

    private val rootScreens = setOf(R.id.localeListFragment)

    override fun showLocaleList() {
        navController.navigate(R.id.localeListFragment)
    }

    override fun showLocaleForm(locale: LocaleBinding) {
        val args = Bundle().apply {
            putParcelable(EXTRA_LOCALE, locale)
        }

        navController.navigate(R.id.action_localeListFragment_to_localeFormFragment, args)
    }

    override fun showLocaleTabs(locale: LocaleBinding) {
        val args = Bundle().apply {
            putParcelable(EXTRA_LOCALE, locale)
        }

        navController.navigate(R.id.action_localeListFragment_to_localeTabFragment, args)
    }

    override fun showPatrimonyDetails(patrimony: PatrimonyBinding) {
        val args = Bundle().apply {
            putParcelable(EXTRA_PATRIMONY, patrimony)
        }

        navController.navigate(R.id.action_localeTabFragment_to_patrimonyDetailsFragment, args)
    }

    override fun showPatrimonyNew(patrimony: PatrimonyBinding) {
        val args = Bundle().apply {
            putParcelable(EXTRA_PATRIMONY, patrimony)
        }

        navController.navigate(R.id.action_localeTabFragment_to_patrimonyFormFragment, args)
    }

    override fun showPatrimonyEdit(patrimony: PatrimonyBinding) {
        val args = Bundle().apply {
            putParcelable(EXTRA_PATRIMONY, patrimony)
        }

        navController.navigate(R.id.action_patrimonyDetailsFragment_to_patrimonyFormFragment, args)
    }

    override fun showInventoryTab(inventory: InventoryBinding) {
        val args = Bundle().apply {
            putParcelable(EXTRA_INVENTORY, inventory)
        }

        navController.navigate(R.id.action_localeTabFragment_to_inventoryTabFragment, args)
    }

    override fun showBarcodeReader(inventory: InventoryBinding) {
        val args = Bundle().apply {
            putParcelable(EXTRA_INVENTORY, inventory)
        }

        navController.navigate(R.id.action_inventoryTabFragment_to_inventoryBarcodeFragment, args)
    }

    override fun back() {
        navController.popBackStack()
    }

    override fun getRootScreen(): Set<Int> {
        return rootScreens
    }

    override fun isInRootScreen(): Boolean {
        return rootScreens.contains(navController.currentDestination?.id)
    }

}