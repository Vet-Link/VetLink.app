package com.capstoneproject.vetlink.ui.pet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstoneproject.vetlink.databinding.ActivityPetBinding

class PetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        // Get pet details from intent
        val petName = intent.getStringExtra("petName")
        val petGender = intent.getStringExtra("petGender")
        val petAge = intent.getStringExtra("petAge")
        val petSpecies = intent.getStringExtra("petSpecies")
        val petWeight = intent.getStringExtra("petWeight")

        // Display pet details
        binding.petName.text = "$petName"
        binding.petGender.text = "$petGender"
        binding.petAge.text = "$petAge"
        binding.petSpecies.text = "$petSpecies"
        binding.petWeight.text = "$petWeight"

    }

    private fun setupActionBar() {
        supportActionBar?.title = "Pet Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
