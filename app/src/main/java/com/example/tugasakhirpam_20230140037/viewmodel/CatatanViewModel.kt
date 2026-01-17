package com.example.tugasakhirpam_20230140037.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tugasakhirpam_20230140037.data.repository.CatatanRepository

class CatatanViewModel : ViewModel() {
    private val repository = CatatanRepository()

    // Kamu bisa memindahkan logika Retrofit dari Screen ke sini nanti.
    // Untuk mengejar deadline, biarkan ini sebagai jembatan saja.
    fun getRepository() = repository
}