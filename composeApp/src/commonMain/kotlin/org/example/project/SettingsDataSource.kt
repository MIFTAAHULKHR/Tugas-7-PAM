package org.example.project

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class SortOrder {
    NEWEST, OLDEST, TITLE
}

class SettingsDataSource(private val dataStore: DataStore<Preferences>) {
    private val themeKey = booleanPreferencesKey("is_dark_mode")
    private val sortOrderKey = stringPreferencesKey("sort_order")

    val isDarkMode: Flow<Boolean> = dataStore.data.map { it[themeKey] ?: false }
    val sortOrder: Flow<SortOrder> = dataStore.data.map { 
        SortOrder.valueOf(it[sortOrderKey] ?: SortOrder.NEWEST.name)
    }

    suspend fun setDarkMode(isDark: Boolean) {
        dataStore.edit { it[themeKey] = isDark }
    }

    suspend fun setSortOrder(order: SortOrder) {
        dataStore.edit { it[sortOrderKey] = order.name }
    }
}
