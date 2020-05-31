package com.brocodes.wedoit.commonutils

import java.text.SimpleDateFormat
import java.util.*

fun getDateString(timeInMillis: Long): String {

    val simpleFormatter = SimpleDateFormat("EEE, MMM dd yyyy", Locale.getDefault())

    return simpleFormatter.format(timeInMillis)
}

