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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tugasakhirpam_20230140037.data.api.RetrofitClient
import com.example.tugasakhirpam_20230140037.data.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(navController: NavController, userId: Int) {
    var judul by remember { mutableStateOf("") }
    var isi by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val primaryNavy = Color(0xFF1B3A57)
    val accentBlue = Color(0xFF4A90E2)
    val bgLight = Color(0xFFF4F7FA)

    Scaffold(
        containerColor = bgLight,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Buat Catatan", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primaryNavy)
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            // Header Gradient
            Box(modifier = Modifier.fillMaxWidth().height(40.dp).background(
                Brush.verticalGradient(listOf(primaryNavy, bgLight))
            ))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-20).dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Detail Catatan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = primaryNavy)
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = judul,
                            onValueChange = { judul = it },
                            label = { Text("Judul Materi") },
                            leadingIcon = { Icon(Icons.Default.Title, null, tint = accentBlue) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = isi,
                            onValueChange = { isi = it },
                            label = { Text("Isi Catatan") },
                            leadingIcon = {
                                Box(modifier = Modifier.padding(bottom = 120.dp)) {
                                    Icon(Icons.Default.Description, null, tint = accentBlue)
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (judul.isBlank() || isi.isBlank()) {
                            Toast.makeText(context, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
                        } else {
                            isLoading = true

                            // PERBAIKAN: Pastikan nama parameter (idUser, idKategori, judul, isi)
                            // sesuai dengan yang ada di ApiService.kt
                            RetrofitClient.instance.addCatatan(
                                idUser = userId,
                                idKategori = 1,
                                judul = judul,
                                isi = isi // Pastikan di ApiService menggunakan nama 'isi', bukan 'isi_catatan'
                            ).enqueue(object : Callback<ApiResponse> {
                                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                                    isLoading = false
                                    if (response.isSuccessful && response.body()?.status == true) {
                                        Toast.makeText(context, "Berhasil disimpan", Toast.LENGTH_SHORT).show()
                                        // Kembali ke halaman list agar data di-refresh
                                        navController.popBackStack()
                                    } else {
                                        val errorMsg = response.body()?.message ?: "Gagal menyimpan"
                                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                                    isLoading = false
                                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryNavy),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Simpan Materi", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}