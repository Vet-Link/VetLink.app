package com.capstoneproject.vetlink.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.capstoneproject.vetlink.data.preferences.UserPreferences
import com.capstoneproject.vetlink.data.preferences.datastore
import com.bumptech.glide.Glide
import com.capstoneproject.vetlink.R
import de.hdodenhof.circleimageview.CircleImageView
import com.capstoneproject.vetlink.databinding.FragmentProfileBinding
import com.capstoneproject.vetlink.ui.pet.AddPetActivity
import com.capstoneproject.vetlink.ui.pet.PetActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels {
        viewModelFactory {
            initializer {
                ProfileViewModel(UserPreferences.getInstance(requireContext().datastore))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvUsername
        val email: TextView = binding.tvEmail
        val profileImageView: CircleImageView = binding.ivProfile
        val btPet: TextView = binding.btPet
        val btYourPet: TextView = binding.btYourPet

        profileViewModel.user.observe(viewLifecycleOwner) { profileResponse ->
            Log.d("ProfileFragment", "Observed ProfileResponse: $profileResponse")
            textView.text = profileResponse.data?.username ?: "unknown"
            email.text = profileResponse.data?.email ?: "unknown"

            // Load profile image using Glide
            profileResponse.data?.profileUrl?.let { url ->
                Glide.with(requireContext())
                    .load(profileResponse.data?.profileUrl)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .circleCrop()
                    .into(profileImageView)
            } ?: run {
                profileImageView.setImageResource(R.drawable.default_image)
            }
        }

        btPet.setOnClickListener {
            startActivity(Intent(requireContext(), AddPetActivity::class.java))
        }

        btYourPet.setOnClickListener {
            startActivity(Intent(requireContext(), PetActivity::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
