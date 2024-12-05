package com.smart.task.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.task.R
import com.smart.task.databinding.ActivityMainBinding
import com.smart.task.ui.SharedViewModel
import com.smart.task.ui.UiModule
import com.smart.task.ui.dailyDetails.TaskDetail
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var adapter: TasksAdapter? = null
    private val viewModel by lazy {
        MainModule.getMainViewModel(this)
    }

    private val sharedViewModel: SharedViewModel by lazy {
        UiModule.getSharedViewModel(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rec.layoutManager = layoutManager

        adapter = TasksAdapter {
            navigateToTaskDetail()
            sharedViewModel.postTask(it.id)
        }

        binding.rec.adapter = adapter

        binding.navigatePrevious.setOnClickListener {
            viewModel.getTasksForPreviousDay()
        }

        binding.navigateNext.setOnClickListener {
            viewModel.getTasksForNextDay()
        }

        createObservers()
    }

    private fun navigateToTaskDetail(){
        val taskDetail = TaskDetail()
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .add(R.id.mainRoot, taskDetail, TaskDetail.TAG)
            .addToBackStack(TaskDetail.TAG)
            .commit()
    }


    private fun createObservers() {
        lifecycleScope.launch {
            viewModel.tasks.collect { dataPair ->

                val taskItems = dataPair.second
                binding.title.text = dataPair.first

                if (taskItems.isEmpty()) {
                    if (binding.rec.visibility == View.VISIBLE)
                        binding.rec.visibility = View.GONE
                    if (binding.noDataTextView.visibility == View.GONE)
                        binding.noDataTextView.visibility = View.VISIBLE
                    if (binding.noDataImageView.visibility == View.GONE)
                        binding.noDataImageView.visibility = View.VISIBLE
                } else {
                    adapter?.swapData(taskItems)
                    if (binding.noDataTextView.visibility == View.VISIBLE)
                        binding.noDataTextView.visibility = View.GONE
                    if (binding.noDataImageView.visibility == View.VISIBLE)
                        binding.noDataImageView.visibility = View.GONE
                    if (binding.rec.visibility == View.GONE)
                        binding.rec.visibility = View.VISIBLE

                }
            }
        }

        viewModel.loadingState.observe(this) {
            if (it) showProgressBar()
            else hideProgressBar()
        }

        lifecycleScope.launch {
            sharedViewModel.data.collect{
                it?.let { it1 -> adapter?.updateTaskViewItem(it1) }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (!viewModel.isNetworkAvailable(this)) {
            showNoInternetMessage()
        }
    }

    /*************************PRIVATE FUNCTIONS*************************/

    private fun showSpecialMessage() {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this, "Error happened", Toast.LENGTH_LONG).show()
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showNoInternetMessage() {
        showSpecialMessage()
    }
}

