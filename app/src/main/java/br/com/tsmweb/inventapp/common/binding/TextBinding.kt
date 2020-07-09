package br.com.tsmweb.inventapp.common.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.tsmweb.inventapp.R
import java.text.SimpleDateFormat
import java.util.*

object TextBinding {

    @JvmStatic
    @BindingAdapter("android:text")
    fun setTextFromInt(textView: TextView, value: Int) {
        textView.text = value.toString()
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setTextFromLong(textView: TextView, value: Long) {
        textView.text = value.toString()
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setTextFromDate(textView: TextView, value: Date) {
        val dateFormat = textView.context.resources.getString(R.string.date_format)
        textView.text = SimpleDateFormat(dateFormat).format(value)
    }

}