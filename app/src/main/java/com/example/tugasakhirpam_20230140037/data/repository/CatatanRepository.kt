package com.example.tugasakhirpam_20230140037.data.repository

import com.example.tugasakhirpam_20230140037.data.api.RetrofitClient
import com.example.tugasakhirpam_20230140037.data.model.ApiResponse
import com.example.tugasakhirpam_20230140037.data.model.CatatanResponse
import retrofit2.Call

class CatatanRepository {
    private val apiService = RetrofitClient.instance

    fun getCatatan(userId: Int): Call<List<CatatanResponse>> = apiService.getCatatan(userId)

    fun deleteCatatan(id: Int): Call<ApiResponse> = apiService.deleteCatatan(id)

    // Tambahkan fungsi lain jika diperlukan untuk Add/Edit
}