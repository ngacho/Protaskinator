package com.brocodes.protaskinator.prioritytasks

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.protaskinator.R
import com.brocodes.protaskinator.commonutils.SwipeActionCallBack
import com.brocodes.protaskinator.commonutils.TaskListAdapter
import com.brocodes.protaskinator.databinding.FragmentPriorityTasksBinding
import com.brocodes.protaskinator.edittask.EditTaskFragment
import com.brocodes.protaskinator.mainactivity.MainActivity
import com.brocodes.protaskinator.model.entity.Task
import com.brocodes.protaskinator.notification.AlarmScheduler
import java.util.*


class PriorityTasksFragment : Fragment() {

    lateinit var priorityTasksAdapter: TaskListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val priorityTasksViewModel = (activity as MainActivity).mainActivityViewModel

        val priorityTasksBinding = DataBindingUtil.inflate<FragmentPriorityTasksBinding>(
            inflater,
            R.layout.fragment_priority_tasks,
            container,
            false
        )

        val priorityTasksRecyclerView = priorityTasksBinding.priorityTasksRecyclerview
        priorityTasksRecyclerView.setHasFixedSize(true)

        priorityTasksViewModel.priorityTasks.observe(
            viewLifecycleOwner,
            Observer { priorityTasksList ->
                priorityTasksAdapter =
                    TaskListAdapter(priorityTasksList) { task -> showEditTaskFragment(task) }
                priorityTasksRecyclerView.adapter = priorityTasksAdapter
            })

        val swipeActionsCallBack = object : SwipeActionCallBack(
            requireContext(),
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedTask = priorityTasksAdapter.getTaskAt(viewHolder.adapterPosition)
                if (direction == ItemTouchHelper.RIGHT) {
                    if (swipedTask.date > Calendar.getInstance(Locale.getDefault()).timeInMillis) {
                        AlarmScheduler.cancelAlarm(requireContext(), swipedTask)
                    }
                    //swipe left to right to complete task
                    priorityTasksViewModel.completeTask(swipedTask)
                    Toast.makeText(context, "Task Completed", Toast.LENGTH_SHORT).show()
                } else {
                    if (swipedTask.date > Calendar.getInstance(Locale.getDefault()).timeInMillis) {
                        AlarmScheduler.cancelAlarm(requireContext(), swipedTask)
                    }
                    //swipe right to left to delete task
                    priorityTasksViewModel.deleteTask(swipedTask)
                    Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeActionsCallBack)
        itemTouchHelper.attachToRecyclerView(priorityTasksRecyclerView)


        // Inflate the layout for this fragment
        return priorityTasksBinding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        //Override from main activity so that fragments can be searchable
        val searchItem: MenuItem? = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        //searchView to listen for text and filter recyclerview
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                priorityTasksAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                priorityTasksAdapter.filter.filter(newText)
                return false
            }
        })
        super.onPrepareOptionsMenu(menu)
    }

    private fun showEditTaskFragment(task: Task) {
        val bundle = Bundle()
        bundle.putSerializable("edit_task", task)
        val bottomSheetFragment = EditTaskFragment()
        bottomSheetFragment.arguments = bundle
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

}
