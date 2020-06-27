package br.com.tsmweb.inventapp.common

import androidx.fragment.app.Fragment
import br.com.tsmweb.inventapp.MainActivity
import br.com.tsmweb.presentation.Router

abstract class BaseFragment : Fragment() {

    val router: Router
        get() = (activity as MainActivity).router

}