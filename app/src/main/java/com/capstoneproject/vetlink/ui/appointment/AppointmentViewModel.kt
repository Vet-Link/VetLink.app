package com.capstoneproject.vetlink.ui.appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppointmentViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is appointment Fragment"
    }
    val text: LiveData<String> = _text
}