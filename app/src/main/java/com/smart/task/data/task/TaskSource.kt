package com.smart.task.data.task

import com.smart.task.domain.Task

interface RemoteDataSource {
    suspend fun fetchAll(): List<Task>
}

interface LocalDataSource {
    suspend fun getAll(): List<Task>
    suspend fun getById(id: String): Task?
    suspend fun insertAll(items: List<Task>)
    suspend fun insert(item: Task)
    suspend fun deleteTask(item: Task)
}



