package com.smart.task.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.task.domain.Task
import com.smart.task.usecases.GetAllTasksForTodayUseCase
import com.smart.task.usecases.GetAllTasksUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed class UpdateDataPolicy{
    data object SOURCE: UpdateDataPolicy()
    data object ADD: UpdateDataPolicy()
    data object REMOVE: UpdateDataPolicy()
}

class MainViewModel(
    private val getAllTasksForTodayUseCase: GetAllTasksForTodayUseCase,
) : ViewModel() {

    private val _tasks = MutableSharedFlow<Pair<UpdateDataPolicy, List<TaskViewItem>>>()
    val tasks: SharedFlow<Pair<UpdateDataPolicy, List<TaskViewItem>>> get() = _tasks

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState = _loadingState

    init {
        viewModelScope.launch {
            val todayTasks = getAllTasksForTodayUseCase.invoke()
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val actNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}