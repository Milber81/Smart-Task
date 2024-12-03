package com.smart.task.data.task.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.smart.task.data.task.LocalDataSource
import com.smart.task.domain.Task
import kotlinx.coroutines.flow.first
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json


class DataStoreHelper(
    private val dataStore: DataStore<Preferences>
) : LocalDataSource {

    companion object {
        val SAVED_TASKS = stringPreferencesKey("tasks")
    }

    override suspend fun getAll(): List<Task> {
        val json = dataStore.data.first()[SAVED_TASKS] ?: return emptyList()
        return Json.decodeFromString(json)
    }

    override suspend fun getById(id: String): Task? {
        return getAll().firstOrNull { it.id == id }
    }

    override suspend fun insertAll(items: List<Task>) {
        val currentTasks = getAll().toMutableSet()
        currentTasks.addAll(items)
        val json = Json.encodeToString(ListSerializer(Task.serializer()), currentTasks.toList())
        dataStore.edit { preferences ->
            preferences[SAVED_TASKS] = json
        }
    }

    override suspend fun insert(item: Task) {
        val currentTasks = getAll().toMutableList()
        currentTasks.add(item)
        val json = Json.encodeToString(ListSerializer(Task.serializer()), currentTasks)
        dataStore.edit { preferences ->
            preferences[SAVED_TASKS] = json
        }
    }

    override suspend fun deleteTask(item: Task) {
        val currentTasks = getAll().toMutableList()
        currentTasks.removeIf { it.id == item.id }
        val json = Json.encodeToString(ListSerializer(Task.serializer()), currentTasks)
        dataStore.edit { preferences ->
            preferences[SAVED_TASKS] = json
        }
    }
}

