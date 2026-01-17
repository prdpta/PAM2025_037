package com.example.tugasakhirpam_20230140037.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    /**
     * IP 10.0.2.2 adalah alias untuk localhost di Emulator Android.
     * Sesuaikan PORT (contoh: 3000) dengan port yang digunakan server Node.js kamu.
     * Jangan lupa garis miring (/) di akhir URL.
     */
    private const val BASE_URL = "http://10.0.2.2:3000/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}