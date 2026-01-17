package com.example.tugasakhirpam_20230140037.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: UserData?
)

data class UserData(
    @SerializedName("id_user") val id_user: Int,
    @SerializedName("username") val username: String
)

data class CatatanResponse(
    @SerializedName("id_catatan") val id_catatan: Int,
    @SerializedName("id_user") val id_user: Int,
    @SerializedName("id_kategori") val id_kategori: Int,
    @SerializedName("judul") val judul: String,
    // Kita gunakan nama variabel 'isi' agar sinkron dengan UI HomeScreen/TugasScreen
    @SerializedName("isi_catatan") val isi: String,
    @SerializedName("nama_kategori") val nama_kategori: String? = null,
    @SerializedName("created_at") val created_at: String? = null
)

data class ProfileResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("id_user") val id_user: Int,
    @SerializedName("username") val username: String
)

data class ApiResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)

data class KategoriResponse(
    @SerializedName("id_kategori") val id_kategori: Int,
    @SerializedName("nama_kategori") val nama_kategori: String
)