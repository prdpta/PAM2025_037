package com.example.tugasakhirpam_20230140037.data.api

import com.example.tugasakhirpam_20230140037.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("get_profile")
    fun getProfile(
        @Query("id_user") id_user: Int
    ): Call<ProfileResponse>

    @GET("catatan/{id_user}")
    fun getCatatan(
        @Path("id_user") idUser: Int
    ): Call<List<CatatanResponse>>

    @GET("catatan/detail/{id}")
    fun getCatatanById(
        @Path("id") id: Int
    ): Call<CatatanResponse>

    @FormUrlEncoded
    @POST("catatan")
    fun addCatatan(
        @Field("id_user") idUser: Int,
        @Field("id_kategori") idKategori: Int,
        @Field("judul") judul: String,
        @Field("isi_catatan") isi: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @PUT("catatan/{id}")
    fun updateCatatan(
        @Path("id") idCatatan: Int,
        @Field("judul") judul: String,
        @Field("isi_catatan") isi: String
    ): Call<ApiResponse>

    @DELETE("catatan/{id}")
    fun deleteCatatan(
        @Path("id") id: Int
    ): Call<ApiResponse>
}