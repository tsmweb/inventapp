package br.com.tsmweb.inventapp.common.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.features.patrimony.binding.StatusPatrimony
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
    fun setTextFromDate(textView: TextView, value: Date?) {
        if (value != null) {
            val dateFormat = textView.context.resources.getString(R.string.date_format)
            textView.text = SimpleDateFormat(dateFormat).format(value)
        }
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setTextFromStatusType(textView: TextView, statusPatrimony: StatusPatrimony?) {
        if (statusPatrimony == null) {
            textView.text = null
            return
        }

        val context = textView.context

        when (statusPatrimony) {
            StatusPatrimony.ACTIVE -> textView.text = context.getString(R.string.patrimony_status_active)
            StatusPatrimony.INACTIVE -> textView.text = context.getString(R.string.patrimony_status_inactive)
            StatusPatrimony.DEPRECIATED -> textView.text = context.getString(R.string.patrimony_status_depreciated)
        }
    }

    @JvmStatic
    @BindingAdapter("android:background")
    fun setBackgroundByStatusType(textView: TextView, statusPatrimony: StatusPatrimony?) {
        val context = textView.context

        if (statusPatrimony == null) {
            textView.background = context.getDrawable(R.drawable.bg_rectangle_gray)
            return
        }

        when (statusPatrimony) {
            StatusPatrimony.ACTIVE -> textView.background = context.getDrawable(R.drawable.bg_rectangle_green)
            StatusPatrimony.DEPRECIATED -> textView.background = context.getDrawable(R.drawable.bg_rectangle_gray)
            StatusPatrimony.INACTIVE -> textView.background = context.getDrawable(R.drawable.bg_rectangle_red)
        }
    }

}