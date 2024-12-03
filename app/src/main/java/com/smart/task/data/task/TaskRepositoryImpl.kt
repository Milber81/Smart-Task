package com.smart.task.data.task

import com.smart.task.domain.Task
import com.smart.task.domain.repositories.TaskRepository

class TaskRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : TaskRepository {

    override suspend fun getAll(): List<Task> {
        val localData = localDataSource.getAll()
        if (localData.isNotEmpty()) {
            return localData
        }
        println("ooooooo TaskRepositoryImpl getAll")
        // Fetch from remote if local cache is empty
        val remoteData = remoteDataSource.fetchAll()
        localDataSource.insertAll(remoteData)
        return remoteData
    }

    override suspend fun getById(id: String): Task? {
        return localDataSource.getById(id)
    }

    override suspend fun updateTask(task: Task) {
        localDataSource.deleteTask(task)
        localDataSource.insert(task)
    }

}
