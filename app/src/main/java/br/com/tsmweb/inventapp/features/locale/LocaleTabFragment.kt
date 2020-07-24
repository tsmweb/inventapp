package br.com.tsmweb.inventapp.features.locale

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.Constants.EXTRA_LOCALE
import br.com.tsmweb.inventapp.databinding.FragmentLocaleTabBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyDetailsFragment
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyFormFragment
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyListFragment
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
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

        initFab()

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
        val adapter = LocaleTabsPagerAdapter(this)
        adapter.addFragment(PatrimonyDetailsFragment())
        adapter.addFragment(PatrimonyListFragment.newInstance(locale))

        binding.viewPagerLocale.adapter = adapter

        TabLayoutMediator(binding.tabLayoutLocale, binding.viewPagerLocale) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.inventories)
                1 -> tab.text = getString(R.string.patrimonies)
            }
        }.attach()

        binding.viewPagerLocale.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            Log.i(TAG, "Detalhes Patrimônio")
                        }
                        1 -> {
                            Log.i(TAG, "Novo Patrimônio")
                        }
                    }

                }
            })
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
            when (binding.viewPagerLocale.currentItem) {
                0 -> {
                    Toast.makeText(requireContext(), "Detalhes Patrimônio", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    val patrimony = PatrimonyBinding().apply {
                        localeId = locale?.id ?: ""
                    }
                    PatrimonyFormFragment.newInstance(patrimony).open(parentFragmentManager)
                }
            }
        }
    }

}