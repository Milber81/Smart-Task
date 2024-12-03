package com.smart.task.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.smart.task.SmartTaskApplication
import com.smart.task.data.task.RemoteDataSource
import com.smart.task.data.task.TasksDtoMapper
import com.smart.task.data.task.datasources.DataStoreHelper
import com.smart.task.data.task.datasources.TasksRemoteService


private const val USER_PREFERENCES = "USER_PREFERENCES"

object DataModule {

    private fun providePreferencesDataStore(appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(produceFile = {
            appContext.preferencesDataStoreFile(
                USER_PREFERENCES
            )
        })
    }

    /**
     * Provides an instance of DataStoreHelper initialized with the application's shared preferences.
     *
     * @see DataStoreHelper for details on managing application-specific preferences.
     * @see providePreferencesDataStore to initialize the preferences data store.
     *
     * @param singleton instance of the application used to access the context.
     * @return A configured DataStoreHelper instance for managing preferences.
     */
    val dataStoreHelper =
        DataStoreHelper(providePreferencesDataStore(SmartTaskApplication.instance))

    val remoteDataSource = TasksRemoteService(
        NetworkModule.provideApiClient(),
        CoroutinesModule.provideCoroutineDispatcher(),
        TasksDtoMapper()
    )
}