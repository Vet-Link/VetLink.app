package com.capstoneproject.vetlink.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstoneproject.vetlink.MainActivity
import com.capstoneproject.vetlink.data.dummy.DataDoctor
import com.capstoneproject.vetlink.data.dummy.HomeAdapter
import com.capstoneproject.vetlink.databinding.FragmentHomeBinding
import com.capstoneproject.vetlink.ui.calender.CalenderActivity
import com.capstoneproject.vetlink.ui.pet.PetActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: HomeViewModel by activityViewModels()
    private lateinit var viewAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupButtonListeners()
    }

    private fun setupRecyclerView() {
        viewAdapter = HomeAdapter(getDummyData())
        binding.doctorProfile.apply {
            adapter = viewAdapter
        }
    }


    private fun setupButtonListeners() {
        // Find the ImageButton by its ID
        val calendarButton: ImageButton = binding.buttonCalender

        val petButton:ImageButton=binding.buttonPet

        // Set an OnClickListener on the calendar button
        calendarButton.setOnClickListener {
            // Create an Intent to start the CalenderActivity
            val intent = Intent(activity, CalenderActivity::class.java)
            startActivity(intent)
        }

        petButton.setOnClickListener {
            // Create an Intent to start the CalenderActivity
            val intent = Intent(activity, PetActivity::class.java)
            startActivity(intent)
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}