package br.com.tsmweb.inventapp.common.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.features.patrimony.binding.StatusType
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
    fun setTextFromStatusType(textView: TextView, statusType: StatusType?) {
        if (statusType == null) {
            textView.text = null
            return
        }

        val context = textView.context

        when (statusType) {
            StatusType.ACTIVE -> textView.text = context.getString(R.string.patrimony_status_active)
            StatusType.INACTIVE -> textView.text = context.getString(R.string.patrimony_status_inactive)
            StatusType.DEPRECIATED -> textView.text = context.getString(R.string.patrimony_status_depreciated)
        }
    }

    @JvmStatic
    @BindingAdapter("android:background")
    fun setBackgroundByStatusType(textView: TextView, statusType: StatusType?) {
        val context = textView.context

        if (statusType == null) {
            textView.background = context.getDrawable(R.drawable.bg_rectangle_gray)
            return
        }

        when (statusType) {
            StatusType.ACTIVE -> textView.background = context.getDrawable(R.drawable.bg_rectangle_green)
            StatusType.DEPRECIATED -> textView.background = context.getDrawable(R.drawable.bg_rectangle_gray)
            StatusType.INACTIVE -> textView.background = context.getDrawable(R.drawable.bg_rectangle_red)
        }
    }

}