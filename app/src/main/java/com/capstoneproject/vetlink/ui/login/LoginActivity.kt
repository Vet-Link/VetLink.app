package com.capstoneproject.vetlink.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.capstoneproject.vetlink.MainActivity
import com.capstoneproject.vetlink.data.model.LoginResponse
import com.capstoneproject.vetlink.data.network.ApiConfig
import com.capstoneproject.vetlink.data.preferences.UserPreferences
import com.capstoneproject.vetlink.data.preferences.datastore
import com.capstoneproject.vetlink.databinding.ActivityLoginBinding
import com.capstoneproject.vetlink.ui.signup.SignupActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupTextWatchers()
        setupView()
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

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            // Reset errors
            clearErrors()

            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            val isEmailValid = isValidEmail(email)
            val isPasswordValid = isValidPassword(password)

            if (isEmailValid && isPasswordValid) {
                val client = ApiConfig.getApiService().login(email, password)
                client.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody?.data != null) {
                                // Login berhasil
                                val userPreferences = UserPreferences.getInstance(datastore)
                                lifecycleScope.launch {
                                    userPreferences.saveUserDetails(responseBody.data, email)
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(this@LoginActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                                        navigateToNextScreen()
                                    }
                                }
                            } else {
                                Toast.makeText(this@LoginActivity, "Failed to get token", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val errorResponse = response.errorBody()?.string()
                            if (errorResponse != null) {
                                val message = parseErrorMessage(errorResponse)
                                showErrorMessages(message)
                            } else {
                                Toast.makeText(this@LoginActivity, "Failed to get response body", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Network error", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        binding.signupText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun setupTextWatchers() {
        binding.edLoginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString()
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.emailEditTextLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.edLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                if (password.length in 8..30) {
                    binding.passwordEditTextLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun parseErrorMessage(response: String): String {
        return response
    }

    private fun showErrorMessages(message: String) {
        try {
            val loginResponse = gson.fromJson(message, LoginResponse::class.java)
            if (loginResponse.message != null) {
                if (message.contains("Email is not valid")) {
                    binding.emailEditTextLayout.error = loginResponse.message
                }
                if (message.contains("Invalid Email or Password")) {
                    binding.passwordEditTextLayout.error = loginResponse.message
                }
                if (message.contains("Please verify your account")) {
                    binding.emailEditTextLayout.error = loginResponse.message
                    binding.passwordEditTextLayout.error = null
                }
                // Handle other specific errors here if needed
            } else {
                binding.emailEditTextLayout.error = "Unknown error"
                binding.passwordEditTextLayout.error = "Unknown error"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to parse error message", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearErrors() {
        binding.emailEditTextLayout.error = null
        binding.passwordEditTextLayout.error = null
    }

    private fun navigateToNextScreen() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }

    // Validasi email
    private fun isValidEmail(email: String): Boolean {
        return if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditTextLayout.error = "Email is not valid. Please try again!"
            false
        } else {
            true
        }
    }

    // Validasi password
    private fun isValidPassword(password: String): Boolean {
        return when {
            password.length < 8 -> {
                binding.passwordEditTextLayout.error = "Password should have a minimum length of 8"
                false
            }
            password.length > 30 -> {
                binding.passwordEditTextLayout.error = "Password should have a maximum length of 30"
                false
            }
            else -> {
                true
            }
        }
    }
}
