package com.capstoneproject.vetlink.ui.landing

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.capstoneproject.vetlink.R
import com.capstoneproject.vetlink.databinding.ActivityLandingBinding
import com.capstoneproject.vetlink.ui.login.LoginActivity
import com.capstoneproject.vetlink.ui.signup.DoctorSignupActivity
import com.capstoneproject.vetlink.ui.signup.SignupActivity

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupLoginText()
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
        binding.buttonDoctor.setOnClickListener {
            startActivity(Intent(this@LandingActivity, DoctorSignupActivity::class.java))
        }

        binding.buttonPetOwner.setOnClickListener {
            startActivity(Intent(this@LandingActivity, SignupActivity::class.java))
        }
    }

    private fun setupLoginText() {
        val textView = binding.loginText
        val text = getString(R.string.already_have_an_account_log_in)
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@LandingActivity, LoginActivity::class.java)) // Pastikan LoginActivity ada di paket yang benar
            }
        }

        val start = text.indexOf("Log in")
        val end = start + "Log in".length

        spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#6C5444")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}