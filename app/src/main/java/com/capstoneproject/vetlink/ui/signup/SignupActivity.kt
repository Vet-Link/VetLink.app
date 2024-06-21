package com.capstoneproject.vetlink.ui.signup

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.capstoneproject.vetlink.R
import com.capstoneproject.vetlink.data.model.SignupResponse
import com.capstoneproject.vetlink.data.network.ApiConfig
import com.capstoneproject.vetlink.databinding.ActivitySignupBinding
import com.capstoneproject.vetlink.ui.EmailVerificationActivity
import com.capstoneproject.vetlink.ui.login.LoginActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupTextWatchers()
        setupView()
        setupLoginText()
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
        binding.signupButton.setOnClickListener {
            // Reset errors
            clearErrors()

            val username = binding.edSignupUsername.text.toString()
            val email = binding.edSignupEmail.text.toString()
            val password = binding.edSignupPassword.text.toString()
            val passwordVerify = binding.edSignupVerifyPass.text.toString()

            val isUsernameValid = isValidUsername(username)
            val isEmailValid = isValidEmail(email)
            val isPasswordValid = isValidPassword(password)
            val doPasswordsMatch = doPasswordsMatch(password, passwordVerify)

            if (isUsernameValid && isEmailValid && isPasswordValid && doPasswordsMatch) {
                val client = ApiConfig.getApiService().signup(username, email, password, passwordVerify)
                client.enqueue(object : Callback<SignupResponse> {
                    override fun onResponse(
                        call: Call<SignupResponse>,
                        response: Response<SignupResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null && responseBody.status == "success") {
                                Toast.makeText(this@SignupActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SignupActivity, EmailVerificationActivity::class.java))
                            } else {
                                Toast.makeText(this@SignupActivity, "Failed to sign up", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val errorResponse = response.errorBody()?.string()
                            if (errorResponse != null) {
                                val message = parseErrorMessage(errorResponse)
                                showErrorMessages(message)
                            } else {
                                Toast.makeText(this@SignupActivity, "Failed to get response body", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                        Toast.makeText(this@SignupActivity, "Network error", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                if (!isUsernameValid) {
                    binding.usernameEditTextLayout.error = "Username must not be empty"
                }
                if (!isEmailValid) {
                    binding.emailEditTextLayout.error = "Email is not valid. Please try again!"
                }
                if (!isPasswordValid) {
                    binding.passwordEditTextLayout.error = "Password should be between 8 and 30 characters"
                }
                if (!doPasswordsMatch) {
                    binding.verifyPassEditTextLayout.error = "Passwords do not match"
                }
            }
        }
    }

    private fun setupTextWatchers() {
        binding.edSignupUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val username = s.toString()
                if (username.isNotEmpty()) {
                    binding.usernameEditTextLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.edSignupEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString()
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.emailEditTextLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.edSignupPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                if (password.length in 8..30) {
                    binding.passwordEditTextLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.edSignupVerifyPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passwordVerify = s.toString()
                if (passwordVerify == binding.edSignupPassword.text.toString()) {
                    binding.verifyPassEditTextLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun parseErrorMessage(response: String): String {
        return try {
            val signupResponse = gson.fromJson(response, SignupResponse::class.java)
            signupResponse.message ?: "Unknown error"
        } catch (e: Exception) {
            e.printStackTrace()
            "Failed to parse error message"
        }
    }

    private fun showErrorMessages(message: String) {
        when {
            message.contains("Email is already in use!") -> {
                binding.emailEditTextLayout.error = "Email is already in use! Try logging in."
            }
            message.contains("Username already exists") -> {
                binding.usernameEditTextLayout.error = "Username already exists."
            }
            message.contains("Usernames must not contain symbols") -> {
                binding.usernameEditTextLayout.error = "Usernames must not contain symbols."
            }
            else -> {
                binding.usernameEditTextLayout.error = null
                binding.emailEditTextLayout.error = null
                binding.passwordEditTextLayout.error = null
                binding.verifyPassEditTextLayout.error = null
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearErrors() {
        binding.usernameEditTextLayout.error = null
        binding.emailEditTextLayout.error = null
        binding.passwordEditTextLayout.error = null
        binding.verifyPassEditTextLayout.error = null
    }

    // Validasi username
    private fun isValidUsername(username: String): Boolean {
        return username.isNotEmpty()
    }

    // Validasi email
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Validasi password
    private fun isValidPassword(password: String): Boolean {
        return password.length in 8..30
    }

    // Validasi kesesuaian password
    private fun doPasswordsMatch(password: String, passwordVerify: String): Boolean {
        return password == passwordVerify
    }

    private fun setupLoginText() {
        val textView = binding.loginText
        val text = getString(R.string.already_have_an_account_log_in)
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            }
        }

        val start = text.indexOf("Log in")
        val end = start + "Log in".length

        spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#6C5444")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}
