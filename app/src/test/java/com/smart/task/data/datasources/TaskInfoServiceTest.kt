package com.smart.task.data.datasources

import com.smart.task.domain.Task
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class TaskInfoServiceTest {

    @Test
    fun testTaskStatus(){
        val task = Task("1", "Task Title", "Task Description", System.currentTimeMillis(), null, 1, Task.UNRESOLVED)
        task.status = Task.RESOLVED
        assertEquals(task.status, Task.RESOLVED)
    }

    @Test
    fun testTaskStatusThrowsException(){
        val task = Task("1", "Task Title", "Task Description", System.currentTimeMillis(), null, 1, Task.UNRESOLVED)

        try {
            task.status = 10
            fail("Expected IllegalArgumentException was not thrown.")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message?.contains("Invalid status") == true)
        }
    }

}
