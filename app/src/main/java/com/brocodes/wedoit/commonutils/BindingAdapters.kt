package com.brocodes.wedoit.commonutils

import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.brocodes.wedoit.R

@BindingAdapter("priority")
fun setTextViewColor(textView: TextView, int: Int) = when(int){
    1 -> textView.setBackgroundColor(ContextCompat.getColor(textView.context, R.color.priorityOne))
    2 -> textView.setBackgroundColor(ContextCompat.getColor(textView.context, R.color.priorityTwo))
    3 -> textView.setBackgroundColor(ContextCompat.getColor(textView.context, R.color.priorityThree))
    4 -> textView.setBackgroundColor(ContextCompat.getColor(textView.context, R.color.priorityFour))
    5 -> textView.setBackgroundColor(ContextCompat.getColor(textView.context, R.color.priorityFive))
    else -> textView.setBackgroundColor(ContextCompat.getColor(textView.context, R.color.fragmentsBackground))
}