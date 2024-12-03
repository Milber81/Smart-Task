package com.smart.task.domain.repositories

import com.smart.task.domain.Task

interface TaskRepository {
    suspend fun getAll(): List<Task>
    suspend fun getById(id: String): Task?
    suspend fun updateTask(task: Task)
}
