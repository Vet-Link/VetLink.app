package com.capstoneproject.vetlink.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.capstoneproject.vetlink.data.model.ProfileResponse
import com.capstoneproject.vetlink.data.network.ApiConfig
import com.capstoneproject.vetlink.data.preferences.UserPreferences
import kotlinx.coroutines.flow.first

class ProfileViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    private val client = ApiConfig.getApiService()

    val user = liveData {

        val email = userPreferences.email.first()
        Log.d("ProfileViewModel", "Email from preferences: $email")
        if (email != null) {
            try {
                val response = client.profile(email)
                Log.d("ProfileViewModel", "API Response: $response")
                emit(response)
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error fetching profile", e)
                emit(ProfileResponse(null, "Failed to load profile", "fail"))
            }
        } else {
            Log.d("ProfileViewModel", "No email found in preferences")
            emit(ProfileResponse(null, "No email found", "fail"))
        }
    }
}
