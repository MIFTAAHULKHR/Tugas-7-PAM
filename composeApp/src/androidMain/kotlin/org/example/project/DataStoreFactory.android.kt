package org.example.project

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

actual class DataStoreFactory(private val context: Context) {
    actual fun create(): DataStore<Preferences> {
        return context.dataStore
    }
}
