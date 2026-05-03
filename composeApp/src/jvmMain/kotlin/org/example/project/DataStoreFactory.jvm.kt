package org.example.project

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import java.io.File

actual class DataStoreFactory {
    actual fun create(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                File(System.getProperty("user.home"), "settings.preferences_pb").absolutePath.toPath()
            }
        )
    }
}
