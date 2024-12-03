package com.smart.task.usecases

import com.smart.task.domain.Task
import com.smart.task.domain.repositories.TaskRepository

class GetAllTasksUseCase(private val repository: TaskRepository){
    suspend fun getAllTasks(): List<Task>{
        return repository.getAll()
    }
}
