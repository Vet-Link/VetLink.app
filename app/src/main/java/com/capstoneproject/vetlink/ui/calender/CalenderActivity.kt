package com.capstoneproject.vetlink.ui.calender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstoneproject.vetlink.MainActivity
import com.capstoneproject.vetlink.databinding.ActivityCalenderBinding
import com.capstoneproject.vetlink.databinding.FragmentHomeBinding


class CalenderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize CalendarView and set a date change listener
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "$dayOfMonth/${month + 1}/$year"
            Toast.makeText(this, "Selected Date: $date", Toast.LENGTH_SHORT).show()
        }
        binding.backButton.setOnClickListener {
            val intent = Intent(this, FragmentHomeBinding::class.java)
            startActivity(intent)
        }
    }
}