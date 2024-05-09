package org.d3if3125.miniproject.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = "settings_preference"
)
class SettingsDataStore(private val context: Context) {

    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
        private val SORT_PREFERENCE_KEY = intPreferencesKey("sort_preference")
    }

    val layoutFlow: Flow<Boolean> = context.dataStore.data.map { preferences -> preferences[IS_LIST] ?: true}

    suspend fun saveLayout(isList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIST] = isList
        }
    }
    suspend fun saveSortPreference(index: Int) {
        context.dataStore.edit { preferences ->
            preferences[SORT_PREFERENCE_KEY] = index
        }
    }

    suspend fun getSortPreference(): Int {
        return context.dataStore.data.map { preferences ->
            preferences[SORT_PREFERENCE_KEY] ?: 0
        }.first()
    }
}