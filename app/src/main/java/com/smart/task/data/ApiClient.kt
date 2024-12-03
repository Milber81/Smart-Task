package com.smart.task.data

import com.smart.task.data.task.models.TaskList
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {
    @GET(".")
    suspend fun getTasks(): Response<TaskList>
}
