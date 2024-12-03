package com.smart.task.data.task.datasources

import com.smart.task.base.ListMapper
import com.smart.task.data.ApiClient
import com.smart.task.data.task.models.TaskDto
import com.smart.task.data.task.models.TaskList
import com.smart.task.data.task.RemoteDataSource
import com.smart.task.domain.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

class TasksRemoteService(
    private val api: ApiClient,
    private val dispatcher: CoroutineDispatcher,
    private val mapper: ListMapper<TaskDto, Task>
) : RemoteDataSource {

    override suspend fun fetchAll(): List<Task> {
        return withContext(dispatcher) {
            val response: Response<TaskList> =
                api.getTasks()
            println("ooooooo TasksRemoteService fetchAll ${response.body()}")
            mapper.map(response.body()!!.tasks)
        }
    }
}

