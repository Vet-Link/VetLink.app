package com.capstoneproject.vetlink.ui.message

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstoneproject.vetlink.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupClickListeners()

        return root
    }

    private fun setupClickListeners() {
        binding.cardView1.setOnClickListener { openDetailMessage("Dr. Rodger Struck") }
        binding.cardView2.setOnClickListener { openDetailMessage("Dr. Charolette Baker") }
        binding.cardView3.setOnClickListener { openDetailMessage("Dr. Libby Anderson") }
        binding.cardView4.setOnClickListener { openDetailMessage("Dr. Jennifer El") }
        binding.cardView5.setOnClickListener { openDetailMessage("Dr. Robert D.J") }
        binding.cardView6.setOnClickListener { openDetailMessage("Dr. Nicky L.J") }
    }

    private fun openDetailMessage(doctorName: String) {
        val intent = Intent(requireActivity(), DetailMessageActivity::class.java)
        intent.putExtra(DetailMessageActivity.EXTRA_DOCTOR_NAME, doctorName)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
