package com.example.tecnisis.config.datastore
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "settings")
        val ROLE_KEY = stringPreferencesKey("role_key")
        val ID_KEY = stringPreferencesKey("id_key")
        val TOKEN_KEY = stringPreferencesKey("token_key")
        val ID_PERSON_KEY = stringPreferencesKey("id_person_key")
        val ID_ROLE_KEY = stringPreferencesKey("id_role_key")
    }

    val role: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[ROLE_KEY]
        }

    suspend fun saveRole(role: String) {
        context.dataStore.edit { preferences ->
            preferences[ROLE_KEY] = role
        }
    }

    suspend fun clearRole() {
        context.dataStore.edit { preferences ->
            preferences.remove(ROLE_KEY)
        }
    }

    val id: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[ID_KEY]
        }

    suspend fun saveId(id: String) {
        context.dataStore.edit { preferences ->
            preferences[ID_KEY] = id
        }
    }

    suspend fun clearId() {
        context.dataStore.edit { preferences ->
            preferences.remove(ID_KEY)
        }
    }

    val token: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    val idPerson: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[ID_PERSON_KEY]
        }

    suspend fun saveIdPerson(idPerson: String) {
        context.dataStore.edit { preferences ->
            preferences[ID_PERSON_KEY] = idPerson
        }
    }

    val idRole: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[ID_ROLE_KEY]
        }

    suspend fun saveIdRole(idRole: String) {
        context.dataStore.edit { preferences ->
            preferences[ID_ROLE_KEY] = idRole
        }
    }
}