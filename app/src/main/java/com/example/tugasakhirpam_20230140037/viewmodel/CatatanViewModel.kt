package com.example.tugasakhirpam_20230140037.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tugasakhirpam_20230140037.data.repository.CatatanRepository

class CatatanViewModel : ViewModel() {
    private val repository = CatatanRepository()

    fun getRepository() = repository
}