package com.capstoneproject.vetlink

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.vetlink.data.dummy.DataDoctor
import com.capstoneproject.vetlink.data.preferences.UserPreferences
import com.capstoneproject.vetlink.data.preferences.datastore
import com.capstoneproject.vetlink.databinding.ActivityMainBinding
import com.capstoneproject.vetlink.data.dummy.HomeAdapter
import com.capstoneproject.vetlink.ui.home.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_appointment,
                R.id.navigation_message,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val userPreferences = UserPreferences.getInstance(datastore)
        lifecycleScope.launch {
            val token = userPreferences.authToken.first()
            Log.d("MainActivity", "onCreate: $token")
        }
        sharedViewModel.setDoctorList(getDummyData())
    }

    private fun getDummyData(): List<DataDoctor> {
        return listOf(
            DataDoctor("Dr. Smith", "Veterinary Surgeon"),
            DataDoctor("Dr. Johnson", "Animal Nutritionist"),
            DataDoctor("Dr. Williams", "Veterinary Dentist"),
            DataDoctor("Dr. Brown", "Veterinary Radiologist"),
            DataDoctor("Dr. Jones", "Veterinary Pathologist")
        )
    }

}