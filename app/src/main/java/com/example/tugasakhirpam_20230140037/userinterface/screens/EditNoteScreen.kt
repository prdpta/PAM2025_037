package com.example.tugasakhirpam_20230140037.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tugasakhirpam_20230140037.data.api.RetrofitClient
import com.example.tugasakhirpam_20230140037.data.model.ApiResponse
import com.example.tugasakhirpam_20230140037.data.model.CatatanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(navController: NavController, noteId: Int) {
    var judul by remember { mutableStateOf("") }
    var isi by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var isSaving by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Definisi Warna Tema (Konsisten dengan Home/Profil)
    val primaryNavy = Color(0xFF1B3A57)
    val accentBlue = Color(0xFF4A90E2)
    val bgLight = Color(0xFFF4F7FA)

    // 1. Ambil data catatan lama
    LaunchedEffect(Unit) {
        RetrofitClient.instance.getCatatanById(noteId).enqueue(object : Callback<CatatanResponse> {
            override fun onResponse(call: Call<CatatanResponse>, response: Response<CatatanResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        judul = it.judul
                        isi = it.isi
                    }
                } else {
                    Toast.makeText(context, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            }
            override fun onFailure(call: Call<CatatanResponse>, t: Throwable) {
                isLoading = false
                Toast.makeText(context, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    Scaffold(
        containerColor = bgLight,
        topBar = {
            TopAppBar(
                title = { Text("Edit Catatan", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryNavy)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // --- Header Background Gradient ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                    .background(Brush.verticalGradient(listOf(primaryNavy, accentBlue)))
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = accentBlue)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .offset(y = (-40).dp) // Mengangkat card agar menimpa background
                ) {
                    // --- Card Input ---
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                "Detail Catatan",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryNavy,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Input Judul
                            OutlinedTextField(
                                value = judul,
                                onValueChange = { judul = it },
                                label = { Text("Judul Materi") },
                                leadingIcon = { Icon(Icons.Default.Title, null, tint = accentBlue) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = accentBlue,
                                    unfocusedBorderColor = Color.LightGray
                                ),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Input Isi
                            OutlinedTextField(
                                value = isi,
                                onValueChange = { isi = it },
                                label = { Text("Isi Catatan") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Description,
                                        null,
                                        tint = accentBlue,
                                        modifier = Modifier.padding(bottom = 120.dp) // Mengatur ikon ke atas
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = accentBlue,
                                    unfocusedBorderColor = Color.LightGray
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // --- Tombol Simpan ---
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = !isSaving,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryNavy),
                        onClick = {
                            if (judul.isBlank() || isi.isBlank()) {
                                Toast.makeText(context, "Judul dan isi tidak boleh kosong", Toast.LENGTH_SHORT).show()
                            } else {
                                isSaving = true
                                RetrofitClient.instance.updateCatatan(
                                    idCatatan = noteId,
                                    judul = judul,
                                    isi = isi
                                ).enqueue(object : Callback<ApiResponse> {
                                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                                        isSaving = false
                                        if (response.isSuccessful && response.body()?.status == true) {
                                            Toast.makeText(context, "Catatan berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                                            navController.popBackStack()
                                        } else {
                                            Toast.makeText(context, "Gagal menyimpan perubahan", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                                        isSaving = false
                                        Toast.makeText(context, "Koneksi Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            }
                        }
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        } else {
                            Text("SIMPAN PERUBAHAN", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        }
                    }
                }
            }
        }
    }
}