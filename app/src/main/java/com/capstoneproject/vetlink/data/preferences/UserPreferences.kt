package com.capstoneproject.vetlink.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    val authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH_TOKEN]
        }

    val email: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_EMAIL]
        }

    val petName: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_PET_NAME]
        }

    val petGender: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_PET_GENDER]
        }

    val petAge: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_PET_AGE]
        }

    val petSpecies: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_PET_SPECIES]
        }

    val petWeight: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_PET_WEIGHT]
        }


    suspend fun saveUserDetails(token: String, email: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = token
            preferences[KEY_EMAIL] = email
        }
    }

    suspend fun savePetDetails(name: String, gender: String, age: String, species: String, weight: String) {
        dataStore.edit { preferences ->
            preferences[KEY_PET_NAME] = name
            preferences[KEY_PET_GENDER] = gender
            preferences[KEY_PET_AGE] = age
            preferences[KEY_PET_SPECIES] = species
            preferences[KEY_PET_WEIGHT] = weight
        }
    }

    suspend fun clearUserDetails() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun clearPetDetails() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_PET_NAME)
            preferences.remove(KEY_PET_GENDER)
            preferences.remove(KEY_PET_AGE)
            preferences.remove(KEY_PET_SPECIES)
            preferences.remove(KEY_PET_WEIGHT)
        }
    }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = token
        }
    }

    suspend fun clearAuthToken() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_AUTH_TOKEN)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }

        private val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_PET_NAME = stringPreferencesKey("pet_name")
        private val KEY_PET_GENDER = stringPreferencesKey("pet_gender")
        private val KEY_PET_AGE = stringPreferencesKey("pet_age")
        private val KEY_PET_SPECIES = stringPreferencesKey("pet_species")
        private val KEY_PET_WEIGHT = stringPreferencesKey("pet_weight")
    }
}
