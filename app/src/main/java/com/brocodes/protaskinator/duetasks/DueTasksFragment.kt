package com.brocodes.protaskinator.duetasks

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
import com.brocodes.protaskinator.databinding.FragmentDueTasksBinding
import com.brocodes.protaskinator.edittask.EditTaskFragment
import com.brocodes.protaskinator.mainactivity.MainActivity
import com.brocodes.protaskinator.model.entity.Task
import com.brocodes.protaskinator.notification.AlarmScheduler
import java.util.*

class DueTasksFragment : Fragment() {

    private lateinit var dueTasksListAdapter: TaskListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dueTasksViewModel = (activity as MainActivity).mainActivityViewModel

        val dueTasksBinding = DataBindingUtil.inflate<FragmentDueTasksBinding>(
            inflater,
            R.layout.fragment_due_tasks,
            container,
            false
        )

        val dueTasksRecyclerView = dueTasksBinding.dueTasksRecyclerview
        dueTasksRecyclerView.setHasFixedSize(true)
        val today = Calendar.getInstance().timeInMillis
        dueTasksViewModel.incompleteTasks.observe(
            viewLifecycleOwner,
            Observer { incompleteTaskList ->
                val dueTasks = arrayListOf<Task>()
                incompleteTaskList.forEach {
                    if (it.date - today < 86400000)
                        dueTasks.add(it)

                }
                dueTasksListAdapter =
                    TaskListAdapter(dueTasks) { task: Task -> showEditTaskFragment(task) }
                dueTasksRecyclerView.adapter = dueTasksListAdapter
            })

        val swipeActionCallBack = object :
            SwipeActionCallBack(
                requireContext(),
                0,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val selectedTask = dueTasksListAdapter.getTaskAt(viewHolder.adapterPosition)
                if (direction == ItemTouchHelper.RIGHT) {
                    if (selectedTask.date > Calendar.getInstance(Locale.getDefault()).timeInMillis) {
                        AlarmScheduler.cancelAlarm(requireContext(), selectedTask)
                    }
                    dueTasksViewModel.completeTask(selectedTask)
                    Toast.makeText(context, "Task Marked Incomplete", Toast.LENGTH_SHORT).show()
                } else {
                    if (selectedTask.date > Calendar.getInstance(Locale.getDefault()).timeInMillis) {
                        AlarmScheduler.cancelAlarm(requireContext(), selectedTask)
                    }
                    dueTasksViewModel.deleteTask(selectedTask)
                    Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
                }
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeActionCallBack)
        itemTouchHelper.attachToRecyclerView(dueTasksRecyclerView)


        // Inflate the layout for this fragment
        return dueTasksBinding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        //Override from main activity so that fragments can be searchable
        val searchItem: MenuItem? = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        //searchView to listen for text and filter recyclerview
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                dueTasksListAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dueTasksListAdapter.filter.filter(newText)
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
