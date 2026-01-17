package com.example.tugasakhirpam_20230140037.data.api

import com.example.tugasakhirpam_20230140037.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // 1. LOGIN
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    // 2. PROFILE (PENTING: Menggunakan @Query agar sesuai dengan Node.js get_profile)
    @GET("get_profile")
    fun getProfile(
        @Query("id_user") id_user: Int
    ): Call<ProfileResponse>

    // 3. AMBIL SEMUA CATATAN (Berdasarkan ID User)
    @GET("catatan/{id_user}")
    fun getCatatan(
        @Path("id_user") idUser: Int
    ): Call<List<CatatanResponse>>

    // 4. DETAIL CATATAN (Untuk mengambil data sebelum diedit)
    @GET("catatan/detail/{id}")
    fun getCatatanById(
        @Path("id") id: Int
    ): Call<CatatanResponse>

    // 5. TAMBAH CATATAN (Lengkap dengan id_kategori)
    @FormUrlEncoded
    @POST("catatan")
    fun addCatatan(
        @Field("id_user") idUser: Int,
        @Field("id_kategori") idKategori: Int,
        @Field("judul") judul: String,
        @Field("isi_catatan") isi: String
    ): Call<ApiResponse>

    // 6. UPDATE CATATAN (Menggunakan @Path untuk ID catatan)
    @FormUrlEncoded
    @PUT("catatan/{id}")
    fun updateCatatan(
        @Path("id") idCatatan: Int,
        @Field("judul") judul: String,
        @Field("isi_catatan") isi: String
    ): Call<ApiResponse>

    // 7. DELETE CATATAN
    @DELETE("catatan/{id}")
    fun deleteCatatan(
        @Path("id") id: Int
    ): Call<ApiResponse>
}