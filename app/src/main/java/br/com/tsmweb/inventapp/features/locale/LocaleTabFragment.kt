package br.com.tsmweb.inventapp.features.locale

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
import br.com.tsmweb.inventapp.common.Constants.EXTRA_LOCALE
import br.com.tsmweb.inventapp.databinding.FragmentLocaleTabBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import com.google.android.material.tabs.TabLayoutMediator

class LocaleTabFragment : BaseFragment() {

    private val TAG = LocaleTabFragment::class.simpleName

    private lateinit var binding: FragmentLocaleTabBinding

    private val locale: LocaleBinding? by lazy {
        arguments?.getParcelable<LocaleBinding>(EXTRA_LOCALE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocaleTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(binding.toolbar, navHostFragment)

        binding.toolbar.apply {
            title = locale?.name
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        locale?.let {
            initTabLayout(it)
        }
    }

    private fun initTabLayout(locale: LocaleBinding) {
        binding.viewPagerLocale.adapter = LocaleTabsPagerAdapter(this, locale)

        TabLayoutMediator(binding.tabLayoutLocale, binding.viewPagerLocale) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.inventories)
                1 -> tab.text = getString(R.string.patrimonies)
            }
        }.attach()
    }

}