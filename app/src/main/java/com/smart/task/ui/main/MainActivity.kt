package com.smart.task.ui.main

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.smart.task.databinding.ActivityMainBinding
import com.smart.task.domain.Task
import com.smart.task.ui.SharedViewModel
import com.smart.task.ui.UiModule
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var adapter: TasksAdapter? = null
    private val viewModel: MainViewModel by lazy {
        MainModule.provideMainViewModel()
    }

    private val sharedViewModel: SharedViewModel by lazy {
        UiModule.provideMainViewModel
    }

    private lateinit var binding: ActivityMainBinding

    private var isFirstAppStart: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rec.layoutManager = layoutManager

        adapter = TasksAdapter(null, {

        }, {

        })

        binding.rec.adapter = adapter

        binding.navigatePrevious.setOnClickListener {
            viewModel.getTasksForPreviousDay()
        }

        binding.navigateNext.setOnClickListener {
            viewModel.getTasksForNextDay()
        }

        createObservers()
    }


    private fun createObservers() {
        lifecycleScope.launch {
            viewModel.tasks.collect { dataPair ->

                val taskItems = dataPair.second
                binding.title.text = dataPair.first
                println("oooooooo TASKS: $taskItems")

                if (taskItems.isEmpty()) {
                    if(binding.rec.visibility == View.VISIBLE)
                        binding.rec.visibility = View.GONE
                    if(binding.noDataTextView.visibility == View.GONE)
                        binding.noDataTextView.visibility = View.VISIBLE
                    if(binding.noDataImageView.visibility == View.GONE)
                        binding.noDataImageView.visibility = View.VISIBLE
                } else {
                    println("oooooooo TASKS: ------------------")
                    adapter?.swapData(taskItems)
                    if(binding.noDataTextView.visibility == View.VISIBLE)
                        binding.noDataTextView.visibility = View.GONE
                    if(binding.noDataImageView.visibility == View.VISIBLE)
                        binding.noDataImageView.visibility = View.GONE
                    if(binding.rec.visibility == View.GONE)
                        binding.rec.visibility = View.VISIBLE

                }
            }
        }

        viewModel.loadingState.observe(this) {
            if (it) showProgressBar()
            else hideProgressBar()
        }
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.isNetworkAvailable(this)) {
            if (isFirstAppStart) {
                AppCompatDelegate.getApplicationLocales()

                showSpecialMessage()
            }
        } else {
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

    override fun onDestroy() {

        super.onDestroy()
        Glide.get(this).clearMemory()
    }
}

