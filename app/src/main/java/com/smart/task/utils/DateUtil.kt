package com.smart.task.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


fun isToday(timestamp: Long): Boolean {
    // Get the current date
    val today = LocalDate.now()

    // Convert the timestamp to a LocalDate
    val dateToCheck = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    // Compare the dates
    return today == dateToCheck
}
