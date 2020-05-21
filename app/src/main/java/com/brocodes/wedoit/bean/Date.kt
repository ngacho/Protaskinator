package com.brocodes.wedoit.bean

import android.util.Log
import java.util.*

class Date{

    var day : String = GregorianCalendar().get(Calendar.DAY_OF_WEEK).toString()
    var date : String = GregorianCalendar().get(Calendar.DATE).toString()

    init {
        Log.d("Date", "Today is $day of $date")
    }

}