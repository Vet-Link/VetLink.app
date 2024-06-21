package com.capstoneproject.vetlink.ui.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.vetlink.databinding.ActivityDetailMessageBinding

class DetailMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMessageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val doctorName = intent.getStringExtra(EXTRA_DOCTOR_NAME)
        binding.messageTextView.text = getMessageForDoctor(doctorName ?: "")
    }

    private fun getMessageForDoctor(doctorName: String): String {
        return when (doctorName) {
            "Dr. Rodger Struck" -> "Doctor, my cat hasn't eaten for two days. What should I do?"
            "Dr. Charolette Baker" -> "Are there any other symptoms you have noticed, such as vomiting or diarrhea?"
            "Dr. Libby Anderson" -> "Nothing, it's just that he looks lethargic."
            "Dr. Jennifer El" -> "Bring your cat to the clinic for a further examination."
            "Dr. Robert D.J" -> "Okay Doctor, thank you!"
            "Dr. Nicky L.J" -> "Okay Doctor, thank you!"
            else -> ""
        }
    }

    companion object {
        const val EXTRA_DOCTOR_NAME = "extra_doctor_name"
    }
}
