package br.com.tsmweb.inventapp.common

import androidx.fragment.app.Fragment
import br.com.tsmweb.inventapp.MainActivity

abstract class BaseFragment : Fragment() {

    val router: Router
        get() = (activity as MainActivity).router

}