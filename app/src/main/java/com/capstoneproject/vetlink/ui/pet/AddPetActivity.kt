package com.capstoneproject.vetlink.ui.pet

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.capstoneproject.vetlink.data.model.PetResponse
import com.capstoneproject.vetlink.data.network.ApiConfig
import com.capstoneproject.vetlink.data.preferences.UserPreferences
import com.capstoneproject.vetlink.data.preferences.datastore
import com.capstoneproject.vetlink.databinding.ActivityAddPetBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupSubmitButton()
    }

    private fun setupActionBar() {
        supportActionBar?.title = "Add Pet"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupSubmitButton() {
        binding.petAddButton.setOnClickListener {
            // Reset errors
            clearErrors()

            val petName = binding.petAddName.text.toString().trim()
            val petGender = binding.petAddGender.toString().trim()
            val petAge = binding.petAddAge.text.toString().trim()
            val petSpecies = binding.petAddSpecies.text.toString().trim()
            val petWeight = binding.petAddWeight.text.toString().trim()

            val isPetNameValid = isValidField(petName)
            val isPetGenderValid = isValidField(petGender)
            val isPetAgeValid = isValidField(petAge)
            val isPetSpeciesValid = isValidField(petSpecies)
            val isPetWeightValid = isValidField(petWeight)

            if (isPetNameValid && isPetGenderValid && isPetAgeValid && isPetSpeciesValid && isPetWeightValid) {
                val client = ApiConfig.getApiService().addPet(petName, petGender, petAge, petSpecies, petWeight)
                client.enqueue(object : Callback<PetResponse> {
                    override fun onResponse(call: Call<PetResponse>, response: Response<PetResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null && responseBody.status == "success") {
                                Toast.makeText(this@AddPetActivity, responseBody.message, Toast.LENGTH_SHORT).show()

                                // Save pet details to preferences
                                lifecycleScope.launch {
                                    savePetDetailsToPreferences(petName, petGender, petAge, petSpecies, petWeight)

                                    // Start PetActivity and pass data
                                    startActivity(Intent(this@AddPetActivity, PetActivity::class.java).apply {
                                        putExtra("petName", petName)
                                        putExtra("petSpecies", petSpecies)
                                        putExtra("petAge", petAge)
                                        putExtra("petSpecies", petSpecies)
                                        putExtra("petWeight", petWeight)
                                    })
                                    finish()
                                }
                            } else {
                                Toast.makeText(this@AddPetActivity, "Failed to add pet", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val errorResponse = response.errorBody()?.string()
                            if (errorResponse != null) {
                                val message = parseErrorMessage(errorResponse)
                                showErrorMessages(message)
                            } else {
                                Toast.makeText(this@AddPetActivity, "Failed to get response body", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<PetResponse>, t: Throwable) {
                        Toast.makeText(this@AddPetActivity, "Network error", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                if (!isPetNameValid) {
                    binding.petAddName.error = "Pet name must not be empty"
                }
                if (!isPetGenderValid) {
                    binding.petAddGender.error = "Pet gender must not be empty"
                }
                if (!isPetAgeValid) {
                    binding.petAddAge.error = "Pet age must not be empty"
                }
                if (!isPetSpeciesValid) {
                    binding.petAddSpecies.error = "Pet species must not be empty"
                }
                if (!isPetWeightValid) {
                    binding.petAddWeight.error = "Pet weight must not be empty"
                }
            }
        }
    }

    private fun isValidField(text: String): Boolean {
        return text.isNotEmpty()
    }

    private fun clearErrors() {
        binding.petAddName.error = null
        binding.petAddGender.error = null
        binding.petAddAge.error = null
        binding.petAddSpecies.error = null
        binding.petAddWeight.error = null
    }

    private fun parseErrorMessage(errorBody: String): String {
        // Implement your error parsing logic here
        return "Error"
    }

    private fun showErrorMessages(message: String) {
        // Implement your error display logic here
        Toast.makeText(this@AddPetActivity, message, Toast.LENGTH_SHORT).show()
    }

    private suspend fun savePetDetailsToPreferences(petName: String, petGender: String, petAge: String, petSpecies: String, petWeight: String) {
        val userPreferences = UserPreferences.getInstance(applicationContext.datastore)
        userPreferences.savePetDetails(petName, petGender, petAge, petSpecies, petWeight)
    }
}
