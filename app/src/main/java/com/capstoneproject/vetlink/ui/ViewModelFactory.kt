package com.capstoneproject.vetlink.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstoneproject.vetlink.data.preferences.UserPreferences
import com.capstoneproject.vetlink.ui.pet.PetViewModel
import com.capstoneproject.vetlink.ui.profile.ProfileViewModel

class ViewModelFactory(private val userPreferences: UserPreferences) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(userPreferences) as T
        }
        if (modelClass.isAssignableFrom(PetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PetViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
