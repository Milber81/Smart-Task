package com.smart.task.data.task.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TaskList(
    val tasks: List<TaskDto>
)

@Serializable
data class TaskDto(
    val id: String,
    @SerializedName("TargetDate")
    val targetDate: String,
    @SerializedName("DueDate")
    val dueDate: String?,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Priority")
    val priority: Int
)
