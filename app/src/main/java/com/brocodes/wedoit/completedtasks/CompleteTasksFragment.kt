package com.brocodes.wedoit.completedtasks

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.wedoit.R
import com.brocodes.wedoit.commonutils.TaskListAdapter
import com.brocodes.wedoit.databinding.FragmentCompletedTasksBinding
import com.brocodes.wedoit.mainactivity.MainActivity

class CompleteTasksFragment : Fragment() {

    private lateinit var completeTasksListAdapter: TaskListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val completedTasksViewModel = (activity as MainActivity).mainActivityViewModel

        val completeTasksBinding = DataBindingUtil.inflate<FragmentCompletedTasksBinding>(
            inflater,
            R.layout.fragment_completed_tasks,
            container,
            false
        )

        val completeTasksRecyclerView = completeTasksBinding.completedTasksRecyclerview
        completeTasksRecyclerView.setHasFixedSize(true)

        completedTasksViewModel.completeTasks.observe(viewLifecycleOwner, Observer {
            completeTasksListAdapter = TaskListAdapter(it) { Unit }
            completeTasksRecyclerView.adapter = completeTasksListAdapter
        })

        val swipeActionCallBack = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val selectedTask = completeTasksListAdapter.getTaskAt(viewHolder.adapterPosition)
                if (direction == ItemTouchHelper.RIGHT) {
                    completedTasksViewModel.inCompleteTask(selectedTask)
                    Toast.makeText(context, "Task Marked Incomplete", Toast.LENGTH_SHORT).show()
                } else {
                    completedTasksViewModel.deleteTask(selectedTask)
                    Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
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

                val icon: Drawable
                val paint = Paint()
                if (dX > 0) {
                    //Drawing when swiping left to right
                    paint.color =
                        ContextCompat.getColor(requireContext(), R.color.completeTaskColor)
                    val background = RectF(
                        itemView.left.toFloat(),
                        itemView.top.toFloat(),
                        dX,
                        itemView.bottom.toFloat()
                    )
                    canvas.drawRect(background, paint)
                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_incomplete)!!
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
                    paint.color = ContextCompat.getColor(requireContext(), R.color.deleteTaskColor)
                    val background = RectF(
                        itemView.left.toFloat() + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat()
                    )
                    canvas.drawRect(background, paint)
                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)!!
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


        val itemTouchHelper = ItemTouchHelper(swipeActionCallBack)
        itemTouchHelper.attachToRecyclerView(completeTasksRecyclerView)

        // Inflate the layout for this fragment
        return completeTasksBinding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        //Override from main activity so that fragments can be searchable
        val searchItem: MenuItem? = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        //searchView to listen for text and filter recyclerview
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("Completed Tasks Search", query.toString())
                completeTasksListAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("Completed Tasks Search", newText.toString())
                completeTasksListAdapter.filter.filter(newText)
                return false
            }
        })
        super.onPrepareOptionsMenu(menu)
    }
}
