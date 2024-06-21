package com.capstoneproject.vetlink.ui.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstoneproject.vetlink.databinding.FragmentAppointmentBinding

class AppointmentFragment : Fragment() {

    private var _binding: FragmentAppointmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val appointmentViewModel =
            ViewModelProvider(this).get(AppointmentViewModel::class.java)

        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAppointment
        appointmentViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}