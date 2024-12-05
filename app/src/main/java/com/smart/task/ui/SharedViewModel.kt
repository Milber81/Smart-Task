package com.smart.task.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.task.ui.main.TaskViewItem
import com.smart.task.ui.main.TaskViewMapper
import com.smart.task.usecases.AddTaskCommentUseCase
import com.smart.task.usecases.GetTaskByIdUseCase
import com.smart.task.usecases.SetTaskStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val setTaskStatusUseCase: SetTaskStatusUseCase,
    private val addTaskCommentUseCase: AddTaskCommentUseCase,
    private val singleTaskMapper: TaskViewMapper
    ) : ViewModel() {

    private val _data = MutableStateFlow<TaskViewItem?>(null)
    val data: StateFlow<TaskViewItem?> get() = _data

    fun postTask(taskId: String) {
        viewModelScope.launch {
            _data.value = null
            val mTask = getTaskByIdUseCase.invoke(taskId)
            mTask?.let {
                _data.emit(singleTaskMapper.map(it))
            }
        }
    }

    fun resolveTask(taskId: String, status: Int){
        viewModelScope.launch {
            setTaskStatusUseCase.invoke(taskId, status)
            postTask(taskId)
        }
    }

    fun resolveTaskWithComment(taskId: String, status: Int, comment: String){
        viewModelScope.launch {
            comment.let {
                addTaskCommentUseCase.invoke(comment, status, taskId)
            }
            postTask(taskId)
        }
    }
}