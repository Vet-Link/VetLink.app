package com.capstoneproject.vetlink.ui.signup

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.capstoneproject.vetlink.R
import com.google.android.material.textfield.TextInputEditText

class DoctorSignupActivity : AppCompatActivity() {

    private lateinit var doctorSignupButton: Button
    private lateinit var spSpeciality: Spinner
    private lateinit var edSignupUsername: TextInputEditText
    private lateinit var edSignupEmail: TextInputEditText
    private lateinit var edSignupPassword: TextInputEditText
    private lateinit var edSignupVerifyPass: TextInputEditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_signup)

        doctorSignupButton = findViewById(R.id.doctorSignupButton)
        spSpeciality = findViewById(R.id.spSpeciality)
        edSignupUsername = findViewById(R.id.ed_signup_username)
        edSignupEmail = findViewById(R.id.ed_signup_email)
        edSignupPassword = findViewById(R.id.ed_signup_password)
        edSignupVerifyPass = findViewById(R.id.ed_signup_verify_pass)

        // Setup Spinner
        val specialities = resources.getStringArray(R.array.specialities_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, specialities)
        spSpeciality.adapter = adapter

        // Setup sign up button click listener
        doctorSignupButton.setOnClickListener {
            signUp()
        }

        // Setup view
        setupView()
    }

    private fun signUp() {
        // Retrieve user input and perform sign up logic
        val username = edSignupUsername.text.toString().trim()
        val email = edSignupEmail.text.toString().trim()
        val password = edSignupPassword.text.toString().trim()
        val passwordVerify = edSignupVerifyPass.text.toString().trim()
        val speciality = spSpeciality.selectedItem.toString()

        // Validate input and proceed with sign up
        // Implement your sign up logic here
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}
