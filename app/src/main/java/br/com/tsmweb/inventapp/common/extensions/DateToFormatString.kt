package br.com.tsmweb.inventapp.common.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toView(dateFormat: String): String {
    return SimpleDateFormat(dateFormat).format(this)
}