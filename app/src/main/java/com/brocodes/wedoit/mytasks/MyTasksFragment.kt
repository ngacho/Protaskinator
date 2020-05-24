package com.brocodes.wedoit.mytasks

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.wedoit.R
import com.brocodes.wedoit.commonutils.TaskListAdapter
import com.brocodes.wedoit.databinding.FragmentMyTasksBinding
import com.brocodes.wedoit.mainactivity.MainActivity
import com.brocodes.wedoit.model.entity.Task
import com.brocodes.wedoit.mytasks.viewmodel.MyTasksViewModel
import com.brocodes.wedoit.mytasks.viewmodel.MyTasksViewModelFactory

class MyTasksFragment : Fragment() {

    private lateinit var taskListAdapter: TaskListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //set up the view model
        val repository = (activity as MainActivity).taskRepository
        val myTasksViewModel = ViewModelProvider(
            viewModelStore,
            MyTasksViewModelFactory(repository)
        )
            .get(MyTasksViewModel::class.java)

        val myTasksBinding = DataBindingUtil.inflate<FragmentMyTasksBinding>(
            inflater,
            R.layout.fragment_my_tasks,
            container,
            false
        )


        val myTasksRecyclerView = myTasksBinding.myTasksRecyclerview
        myTasksRecyclerView.apply {
            setHasFixedSize(true)
        }

        myTasksViewModel.allTasks.observe(viewLifecycleOwner, Observer {
            taskListAdapter = TaskListAdapter(it)
            myTasksRecyclerView.adapter = taskListAdapter
        })

        val paint = Paint()
        val swipeCallBack = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.RIGHT) {

                } else {
                    val deletedTask = taskListAdapter.getTaskAt(viewHolder.adapterPosition)
                    myTasksViewModel.deleteTask(deletedTask)
                    taskListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                }
            }

            override fun onChildDrawOver(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                val swipedItemView = viewHolder.itemView
                val height = swipedItemView.height
                val width = height / 3
                val icon: Drawable

                if (dX > 0) {
                    //color when swiped
                    paint.color =
                        ContextCompat.getColor(requireContext(), R.color.completeTaskColor)
                    val background = RectF(
                        swipedItemView.left.toFloat(),
                        swipedItemView.top.toFloat(),
                        dX,
                        swipedItemView.bottom.toFloat()
                    )
                    canvas.drawRect(background, paint)

                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_complete)!!
                    val iconBounds = Rect(
                        swipedItemView.left + width,
                        swipedItemView.top + width,
                        swipedItemView.left + 2 * width,
                        swipedItemView.bottom - width
                    )
                    icon.bounds = iconBounds
                    icon.draw(canvas)


                } else {
                    paint.color = ContextCompat.getColor(requireContext(), R.color.deleteTaskColor)
                    val background = RectF(
                        swipedItemView.right.toFloat() + dX,
                        swipedItemView.top.toFloat(),
                        swipedItemView.right.toFloat(),
                        swipedItemView.bottom.toFloat()
                    )
                    canvas.drawRect(background, paint)

                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)!!
                    val iconBounds = Rect(
                        swipedItemView.right - 2 * width,
                        swipedItemView.top + width,
                        swipedItemView.right - width,
                        swipedItemView.bottom - width
                    )
                    icon.bounds = iconBounds
                    icon.draw(canvas)
                }

                super.onChildDrawOver(
                    canvas,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallBack)
        itemTouchHelper.attachToRecyclerView(myTasksRecyclerView)


        // Inflate the layout for this fragment
        return myTasksBinding.root
    }
}

