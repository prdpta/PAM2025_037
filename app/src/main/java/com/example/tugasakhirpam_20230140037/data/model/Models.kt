package com.example.tugasakhirpam_20230140037.data.model

import com.google.gson.annotations.SerializedName

// 1. Model untuk menangani Login
data class LoginResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: UserData?
)

data class UserData(
    @SerializedName("id_user") val id_user: Int,
    @SerializedName("username") val username: String
)

// 2. Model utama untuk data Catatan & Tugas
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

// 3. Model untuk mengambil data Profil User
data class ProfileResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("id_user") val id_user: Int,
    @SerializedName("username") val username: String
)

// 4. Model untuk feedback API (Insert, Update, Delete)
data class ApiResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)

// 5. Model untuk data Kategori
data class KategoriResponse(
    @SerializedName("id_kategori") val id_kategori: Int,
    @SerializedName("nama_kategori") val nama_kategori: String
)