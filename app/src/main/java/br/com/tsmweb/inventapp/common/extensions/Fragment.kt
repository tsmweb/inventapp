package br.com.tsmweb.inventapp.common.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.showKeyboard() {
    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        showSoftInput(requireActivity().currentFocus, 0)
    }
}

fun Fragment.closeKeyboard() {
    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        if (isActive) {
            hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        }
    }
}