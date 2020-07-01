package com.brocodes.protaskinator.commonutils

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.brocodes.protaskinator.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("priority")
fun setTextViewColor(textView: TextView, int: Int) = when (int) {
    1 -> textView.setBackgroundColor(
        ContextCompat.getColor(
            textView.context, R.color.priorityOne
        )
    )
    2 -> textView.setBackgroundColor(
        ContextCompat.getColor(
            textView.context, R.color.priorityTwo
        )
    )
    3 -> textView.setBackgroundColor(
        ContextCompat.getColor(
            textView.context,
            R.color.priorityThree
        )
    )
    4 -> textView.setBackgroundColor(
        ContextCompat.getColor(
            textView.context, R.color.priorityFour
        )
    )
    5 -> textView.setBackgroundColor(
        ContextCompat.getColor(
            textView.context, R.color.priorityFive
        )
    )
    else -> textView.setBackgroundColor(
        ContextCompat.getColor(
            textView.context,
            R.color.fragmentsBackground
        )
    )
}

@BindingAdapter("dueDate")
fun showDueDate(textView: TextView, dueDate: Long) {
    val simpleFormatter = SimpleDateFormat("EEE, MMM dd yyyy", Locale.getDefault())
    textView.text = textView.context
        .getString(R.string.due_date, simpleFormatter.format(dueDate))
}