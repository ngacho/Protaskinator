package com.brocodes.wedoit.mytasks

import android.graphics.*
import android.os.Bundle
import android.util.Log
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

        val swipeActionsCallBack = object : ItemTouchHelper.SimpleCallback(
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
                    Log.d("Right swipe", "Item completed")
                } else {
                    Log.d("Task swiped", "Position $direction")
                    val task = taskListAdapter.getTaskAt(viewHolder.adapterPosition)
                    myTasksViewModel.deleteTask(task)
                    taskListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                }

            }

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

                //val icon: Drawable
                if (dX > 0) {
                    val paint = Paint()
                    //Swipe right
                    paint.color =
                        ContextCompat.getColor(requireContext(), R.color.completeTaskColor)
                    val background = RectF(
                        itemView.left.toFloat(),
                        itemView.top.toFloat(),
                        dX,
                        itemView.bottom.toFloat()
                    )
                    canvas.drawRect(background, paint)
                    val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_complete)!!
                    val iconBounds = Rect(
                        itemView.left + width,
                        itemView.top + width,
                        itemView.left + 2 * width,
                        itemView.bottom - width
                    )
                    icon.bounds = iconBounds
                    icon.draw(canvas)
                } else {
                    val paint = Paint()
                    //Swipe right
                    paint.color = ContextCompat.getColor(requireContext(), R.color.deleteTaskColor)
                    val background = RectF(
                        itemView.left.toFloat() + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat()
                    )
                    canvas.drawRect(background, paint)
                    val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)!!
                    val iconBounds = Rect(
                        itemView.right - 2 * width,
                        itemView.top + width,
                        itemView.right - width,
                        itemView.bottom - width
                    )
                    icon.bounds = iconBounds
                    icon.draw(canvas)
                }


                super.onChildDraw(
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


        val itemTouchHelper = ItemTouchHelper(swipeActionsCallBack)
        itemTouchHelper.attachToRecyclerView(myTasksRecyclerView)


        // Inflate the layout for this fragment
        return myTasksBinding.root
    }
}