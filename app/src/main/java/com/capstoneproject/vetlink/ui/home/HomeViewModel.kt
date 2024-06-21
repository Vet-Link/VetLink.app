package com.capstoneproject.vetlink.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstoneproject.vetlink.data.dummy.DataDoctor

class HomeViewModel : ViewModel() {

    private val _doctorList = MutableLiveData<List<DataDoctor>>()
    val doctorList: LiveData<List<DataDoctor>> = _doctorList

    fun setDoctorList(list: List<DataDoctor>) {
        _doctorList.value = list
    }

}