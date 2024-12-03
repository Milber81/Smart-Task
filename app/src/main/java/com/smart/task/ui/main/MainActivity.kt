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
import com.smart.task.ui.AddCityFragment
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.rec) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
                topMargin = insets.top
            }

            WindowInsetsCompat.CONSUMED
        }

        createObservers()

        binding.addCityButton.setOnClickListener {
            val addCityFragment = AddCityFragment()

            if (addCityFragment.isAdded) {
                addCityFragment.dismiss()
            }

            addCityFragment.show(supportFragmentManager, AddCityFragment.TAG)
        }
    }


    private fun createObservers() {
        viewModel.loadingState.observe(this) {
            if (it) showProgressBar()
            else hideProgressBar()
        }

        lifecycleScope.launch {
            viewModel.tasks.collect { dataPair ->

                if (adapter == null) {
                    val layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.rec.layoutManager = layoutManager
                }

                dataPair.second.let { cityViewItems ->

                    when (dataPair.first) {
                        is UpdateDataPolicy.ADD -> adapter?.updateCity(cityViewItems[0])
                        is UpdateDataPolicy.REMOVE -> adapter?.removeCity(cityViewItems[0])
                        is UpdateDataPolicy.SOURCE -> {
                            if(adapter == null && cityViewItems.isNotEmpty()) {
                                adapter = TasksAdapter(cityViewItems.toMutableList(), {
                                }, {
                                })
                                binding.rec.adapter = adapter
                            }
                        }
                    }
                }
            }
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

