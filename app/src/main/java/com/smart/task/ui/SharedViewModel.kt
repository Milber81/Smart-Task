package com.smart.task.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.task.ui.main.TaskViewItem
import com.smart.task.ui.main.TaskViewMapper
import com.smart.task.usecases.AddTaskCommentUseCase
import com.smart.task.usecases.GetTaskByIdUseCase
import com.smart.task.usecases.SetTaskResolvedUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SharedViewModel(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val setTaskResolvedUseCase: SetTaskResolvedUseCase,
    private val addTaskCommentUseCase: AddTaskCommentUseCase,
    private val singleTaskMapper: TaskViewMapper
    ) : ViewModel() {
    private val _data = MutableSharedFlow<TaskViewItem?>()
    val data: SharedFlow<TaskViewItem?> get() = _data

    fun postTask(taskId: String) {
        viewModelScope.launch {
            val mTask = getTaskByIdUseCase.invoke(taskId)
            mTask?.let {
                _data.emit(singleTaskMapper.map( it))
            }
        }
    }

    fun resolveTask(taskId: String){
        viewModelScope.launch {
            setTaskResolvedUseCase.invoke(taskId)
            postTask(taskId)
        }
    }
}