package br.com.tsmweb.inventapp.features.locale

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class LocaleTabsPagerAdapter(
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