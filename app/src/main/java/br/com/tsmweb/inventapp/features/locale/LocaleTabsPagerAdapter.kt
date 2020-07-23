package br.com.tsmweb.inventapp.features.locale

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyDetailsFragment
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyListFragment

class LocaleTabsPagerAdapter(
    frag: Fragment,
    private val locale: LocaleBinding
) : FragmentStateAdapter(frag) {

    override fun getItemCount() = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        if (position == 1) {
            return PatrimonyListFragment.newInstance(locale)
        }

        return PatrimonyDetailsFragment()
    }

    companion object {
        private const val NUM_PAGES = 2
    }

}