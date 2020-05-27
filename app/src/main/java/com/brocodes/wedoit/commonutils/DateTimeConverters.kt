package com.brocodes.wedoit.commonutils

import java.text.SimpleDateFormat
import java.util.*

fun getDateString(timeInMillis: Long): String {
    val todayTimeInMillis = Calendar.getInstance().timeInMillis
    val simpleFormatter = SimpleDateFormat("EEE, MMM dd yyyy", Locale.getDefault())

    /**gets the time to be formatted
     * when it is less than or equal to today's date, default is seven days from today's date
     * **/
    val saveDate: Long =
        if ((timeInMillis / (1000 * 3600 * 24)) <= todayTimeInMillis / (1000 * 3600 * 24)) {
            todayTimeInMillis + (1000 * 3600 * 24 * 7)
        } else {
            timeInMillis
        }

    return simpleFormatter.format(saveDate)
}

fun getMillis(timeInString: String): Long {
    val cal = Calendar.getInstance()

    val timeArray = timeInString.split(" ").toTypedArray()
    cal[Calendar.DAY_OF_MONTH] = timeArray[2].toInt()
    cal[Calendar.MONTH] = getMonth(timeArray[1])
    cal[Calendar.YEAR] = timeArray[3].toInt()

    return cal.timeInMillis
}

fun getMonth(monthName: String) = when (monthName) {
    "Jan" -> 1
    "Feb" -> 2
    "Mar" -> 3
    "Apr" -> 4
    "May" -> 5
    "Jun" -> 6
    "Jul" -> 7
    "Aug" -> 8
    "Sep" -> 9
    "Oct" -> 10
    "Nov" -> 11
    "Dec" -> 12
    else -> 0
}

