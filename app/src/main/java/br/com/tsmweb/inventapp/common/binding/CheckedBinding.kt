@file:JvmName("CheckedBinding")
package br.com.tsmweb.inventapp.common.binding

import androidx.databinding.InverseMethod
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.features.patrimony.binding.StatusPatrimony

@InverseMethod("buttonIdToStatusType")
fun statusTypeToButtonId(statusPatrimony: StatusPatrimony?): Int {
    var selectedButtonId = 0

    statusPatrimony?.run {
        selectedButtonId = when (this) {
            StatusPatrimony.ACTIVE -> R.id.rbStatusActive
            StatusPatrimony.DEPRECIATED -> R.id.rbStatusDepreciated
            StatusPatrimony.INACTIVE -> R.id.rbStatusInactive
        }
    }

    return selectedButtonId
}

fun buttonIdToStatusType(selectedButtonId: Int): StatusPatrimony? {
    return when (selectedButtonId) {
        R.id.rbStatusActive -> StatusPatrimony.ACTIVE
        R.id.rbStatusDepreciated -> StatusPatrimony.DEPRECIATED
        R.id.rbStatusInactive -> StatusPatrimony.INACTIVE
        else -> null
    }
}

