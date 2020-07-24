@file:JvmName("CheckedBinding")
package br.com.tsmweb.inventapp.common.binding

import androidx.databinding.InverseMethod
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.features.patrimony.binding.StatusType

@InverseMethod("buttonIdToStatusType")
fun statusTypeToButtonId(statusType: StatusType?): Int {
    var selectedButtonId = 0

    statusType?.run {
        selectedButtonId = when (this) {
            StatusType.ACTIVE -> R.id.rbStatusActive
            StatusType.DEPRECIATED -> R.id.rbStatusDepreciated
            StatusType.INACTIVE -> R.id.rbStatusInactive
        }
    }

    return selectedButtonId
}

fun buttonIdToStatusType(selectedButtonId: Int): StatusType? {
    return when (selectedButtonId) {
        R.id.rbStatusActive -> StatusType.ACTIVE
        R.id.rbStatusDepreciated -> StatusType.DEPRECIATED
        R.id.rbStatusInactive -> StatusType.INACTIVE
        else -> null
    }
}

