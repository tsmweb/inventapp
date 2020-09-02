package br.com.tsmweb.inventapp.features.inventory

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class InventoryTabsPagerAdapter(
    frag: Fragment
) : FragmentStateAdapter(frag) {

    private var fragmentList = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList.get(position)
    }

}