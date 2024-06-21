package com.capstoneproject.vetlink.ui.pet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstoneproject.vetlink.data.network.ApiConfig
import com.capstoneproject.vetlink.data.preferences.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PetViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val apiService = ApiConfig.getApiService()

    val petDetails = liveData {
        val petName = userPreferences.petName.first()
        val petGender = userPreferences.petGender.first()
        val petAge = userPreferences.petAge.first()
        val petSpecies = userPreferences.petSpecies.first()
        val petWeight = userPreferences.petWeight.first()

        Log.d("PetViewModel", "Pet Details: $petName, $petGender, $petAge, $petSpecies, $petWeight")
        if (!petName.isNullOrEmpty() && !petGender.isNullOrEmpty() && !petAge.isNullOrEmpty() && !petSpecies.isNullOrEmpty() && !petWeight.isNullOrEmpty()) {
            emit(PetDetails(petName, petGender, petAge, petSpecies,petWeight ))
        } else {
            emit(PetDetails(null, null, null, null, null))
        }
    }

    fun addPet(
        petName: String,
        petGender: String,
        petAge: String,
        petSpecies: String,
        petWeight: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.addPet(petName, petGender, petAge, petSpecies, petWeight)
            } catch (e: Exception) {
                Log.e("PetViewModel", "Error adding pet", e)
            }
        }
    }


    data class PetDetails(
        val name: String?,
        val gender: String?,
        val age: String?,
        val species: String?,
        val weight: String?
    )
}
