package com.smart.task.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.task.domain.Task
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val _data = MutableSharedFlow<Task?>(replay = 1)
    val data: SharedFlow<Task?> get() = _data

    fun postCity(city: Task?) {
        viewModelScope.launch {
            _data.emit(city)
        }
    }
}