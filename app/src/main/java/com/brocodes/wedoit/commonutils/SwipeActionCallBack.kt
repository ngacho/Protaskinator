package com.brocodes.wedoit.commonutils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.wedoit.R

/**
 * Custom class that extends from swipe action callback.
 * Handles the necessary drawing of icons in the canvas
 * we don't override the onSwiped() method here because we want to handle
 * what happens when swiped differently in different views
 * */
abstract class SwipeActionCallBack(
    private val context: Context,
    dragDir : Int,
    swipeDir : Int
) : ItemTouchHelper.SimpleCallback(dragDir, swipeDir) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val width = itemView.height / 3

        val icon: Drawable
        val paint = Paint()
        if (dX > 0) {
            //Drawing when swiping left to right
            paint.color =
                ContextCompat.getColor(context, R.color.completeTaskColor)
            val background = RectF(
                itemView.left.toFloat(),
                itemView.top.toFloat(),
                dX,
                itemView.bottom.toFloat()
            )
            canvas.drawRect(background, paint)
            icon = ContextCompat.getDrawable(context, R.drawable.ic_complete)!!
            val iconBounds = Rect(
                itemView.left + width,
                itemView.top + width,
                itemView.left + 2 * width,
                itemView.bottom - width
            )
            icon.bounds = iconBounds
            icon.draw(canvas)
        } else {
            //Drawing when Swiping Right to Left
            paint.color = ContextCompat.getColor(context, R.color.deleteTaskColor)
            val background = RectF(
                itemView.left.toFloat() + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            )
            canvas.drawRect(background, paint)
            icon = ContextCompat.getDrawable(context, R.drawable.ic_delete)!!
            val iconBounds = Rect(
                itemView.right - 2 * width,
                itemView.top + width,
                itemView.right - width,
                itemView.bottom - width
            )
            icon.bounds = iconBounds
            icon.draw(canvas)
        }

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}