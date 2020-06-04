package com.brocodes.wedoit.mainactivity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.brocodes.wedoit.MyApplication
import com.brocodes.wedoit.R
import com.brocodes.wedoit.addtask.AddTaskFragment
import com.brocodes.wedoit.databinding.ActivityMainBinding
import com.brocodes.wedoit.mainactivity.viewmodel.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        // Ask Dagger to inject our dependencies
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val mainDataBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(
                this,
                R.layout.activity_main
            )


        val bottomNavigationView = mainDataBinding.bottomNavigationView

        //get the navigation controller
        val navController = Navigation.findNavController(this, R.id.fragment_holder)
        //set up the bottom navigation with navController
        bottomNavigationView.setupWithNavController(navController)
        val addTask = mainDataBinding.addTask
        addTask.setOnClickListener {
            showAddTaskFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val black = ContextCompat.getColor(
            this,
            R.color.codGray
        )

        //Search Bar and interface
        val searchItem: MenuItem? = menu?.findItem(R.id.search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        //changing colors of search and close icon
        val searchIcon = searchView.findViewById(R.id.search_button) as ImageView
        searchIcon.setColorFilter(black)
        val closeIcon = searchView.findViewById(R.id.search_close_btn) as ImageView
        closeIcon.setColorFilter(black)

        //modifying edit text of the search bar
        val searchEditText = searchView.findViewById(R.id.search_src_text) as EditText
        searchEditText.hint = resources.getString(R.string.search_title)
        searchEditText.setHintTextColor(black)
        searchEditText.setTextColor(black)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteAll -> {
                mainActivityViewModel.deleteAllTasks()
                return true
            }
            R.id.darkmode -> {
                //add dark mode feature
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAddTaskFragment() {
        val bottomSheetFragment = AddTaskFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
}
