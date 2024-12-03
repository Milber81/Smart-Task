package com.smart.task.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.task.base.ListMapper
import com.smart.task.domain.Task
import com.smart.task.usecases.GetAllTasksForDayUseCase
import com.smart.task.utils.isToday
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class MainViewModel(
    private val getAllTasksForDayUseCase: GetAllTasksForDayUseCase,
    private val mapper: ListMapper<Task, TaskViewItem>
) : ViewModel() {

    private val _tasks = MutableStateFlow<Pair<String, List<TaskViewItem>>>(Pair("Today", emptyList()))
    val tasks: StateFlow<Pair<String, List<TaskViewItem>>> get() = _tasks

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState = _loadingState

    private val intervalDay = 1000 * 60 * 60 * 24

    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    init {
        println("oooooo vmvmvmvmvmvmvmvmvmv")
        getTasksForToday()
    }

    private fun getTasksForToday() {
        getTasksForDay()
    }

    fun getTasksForPreviousDay() {
        getAllTasksForDayUseCase.setDay(getAllTasksForDayUseCase.getDay() - intervalDay)
        getTasksForDay()
    }

    fun getTasksForNextDay() {
        getAllTasksForDayUseCase.setDay(getAllTasksForDayUseCase.getDay() + intervalDay)
        getTasksForDay()
    }

    private fun getTasksForDay() {
        viewModelScope.launch {
            val tasks = getAllTasksForDayUseCase.invoke()
            val date = getAllTasksForDayUseCase.getDay()
            _loadingState.postValue(false)
            val dateTag = if(isToday(date)) "Today" else sdf.format(date)
            _tasks.emit(Pair(dateTag, mapper.map(tasks)))
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