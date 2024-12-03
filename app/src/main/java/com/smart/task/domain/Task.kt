package com.smart.task.domain

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val title: String,
    val description: String,
    val targetDate: Long,
    val dueDate: Long?,
    val priority: Int,
)
