package com.brocodes.wedoit.mytasks

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.wedoit.R
import com.brocodes.wedoit.commonutils.SwipeActionCallBack
import com.brocodes.wedoit.commonutils.TaskListAdapter
import com.brocodes.wedoit.databinding.FragmentMyTasksBinding
import com.brocodes.wedoit.edittask.EditTaskFragment
import com.brocodes.wedoit.mainactivity.MainActivity
import com.brocodes.wedoit.model.entity.Task

class MyTasksFragment : Fragment() {

    private lateinit var taskListAdapter: TaskListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //set up the view model
        val myTasksViewModel = (activity as MainActivity).mainActivityViewModel

        val myTasksBinding = DataBindingUtil.inflate<FragmentMyTasksBinding>(
            inflater,
            R.layout.fragment_my_tasks,
            container,
            false
        )


        val myTasksRecyclerView = myTasksBinding.myTasksRecyclerview
        myTasksRecyclerView.setHasFixedSize(true)

        myTasksViewModel.incompleteTasks.observe(viewLifecycleOwner, Observer {
            taskListAdapter = TaskListAdapter(it) { task -> showEditTaskFragment(task) }
            myTasksRecyclerView.adapter = taskListAdapter
        })

        val swipeActionsCallBack = object : SwipeActionCallBack(
            requireContext(),
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedTask = taskListAdapter.getTaskAt(viewHolder.adapterPosition)
                if (direction == ItemTouchHelper.RIGHT) {
                    //swipe left to right to complete task
                    myTasksViewModel.completeTask(swipedTask)
                    Toast.makeText(context, "Task Completed", Toast.LENGTH_SHORT).show()
                } else {
                    //swipe right to left to delete task
                    myTasksViewModel.deleteTask(swipedTask)
                    Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeActionsCallBack)
        itemTouchHelper.attachToRecyclerView(myTasksRecyclerView)


        // Inflate the layout for this fragment
        return myTasksBinding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        //Override from main activity so that fragments can be searchable
        val searchItem: MenuItem? = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        //searchView to listen for text and filter recyclerview
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("Completed Tasks Search", query.toString())
                taskListAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("My Tasks Search", newText.toString())
                taskListAdapter.filter.filter(newText)
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