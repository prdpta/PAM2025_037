package com.example.tugasakhirpam_20230140037.userinterface.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tugasakhirpam_20230140037.data.api.RetrofitClient
import com.example.tugasakhirpam_20230140037.data.model.CatatanResponse
import com.example.tugasakhirpam_20230140037.data.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, userId: Int) {
    var listCatatan by remember { mutableStateOf<List<CatatanResponse>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val primaryNavy = Color(0xFF1B3A57)
    val accentBlue = Color(0xFF4A90E2)
    val bgLight = Color(0xFFF4F7FA)

    fun loadData() {
        isLoading = true
        RetrofitClient.instance.getCatatan(userId).enqueue(object : Callback<List<CatatanResponse>> {
            override fun onResponse(call: Call<List<CatatanResponse>>, response: Response<List<CatatanResponse>>) {
                isLoading = false
                if (response.isSuccessful) {
                    listCatatan = response.body() ?: emptyList()
                }
            }
            override fun onFailure(call: Call<List<CatatanResponse>>, t: Throwable) {
                isLoading = false
                Toast.makeText(context, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun deleteNote(noteId: Int) {
        RetrofitClient.instance.deleteCatatan(noteId).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Catatan dihapus", Toast.LENGTH_SHORT).show()
                    loadData()
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal menghapus", Toast.LENGTH_SHORT).show()
            }
        })
    }

    LaunchedEffect(Unit) { loadData() }

    val filteredList = listCatatan.filter {
        it.judul.contains(searchQuery, ignoreCase = true) || it.isi.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        containerColor = bgLight,
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier.height(70.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomNavItem(Icons.Default.Home, "Home", primaryNavy) { loadData() }

                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(primaryNavy)
                            .clickable { navController.navigate("add_note/$userId") },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, null, tint = Color.White, modifier = Modifier.size(28.dp))
                    }

                    BottomNavItem(Icons.Default.Person, "Profil", Color.Gray) {
                        navController.navigate("profile/$userId")
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                        .background(Brush.verticalGradient(listOf(primaryNavy, accentBlue)))
                        .padding(24.dp)
                ) {
                    Text(
                        "Catatan Belajar",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
            }

            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .offset(y = (-25).dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 6.dp
                ) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Cari materi...") },
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = accentBlue) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (isLoading) {
                item {
                    Box(modifier = Modifier.fillParentMaxHeight(0.6f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = accentBlue)
                    }
                }
            } else if (filteredList.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillParentMaxHeight(0.6f).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.Description, null, modifier = Modifier.size(60.dp), tint = Color.LightGray)
                        Text("Tidak ada catatan ditemukan", color = Color.Gray)
                    }
                }
            } else {
                items(filteredList) { catatan ->
                    ModernNoteCard(
                        catatan = catatan,
                        accent = accentBlue,
                        onEdit = {
                            navController.navigate("edit_note/${catatan.id_catatan}")
                        },
                        onDelete = {
                            deleteNote(catatan.id_catatan)
                        }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}


@Composable
fun ModernNoteCard(catatan: CatatanResponse, accent: Color, onEdit: () -> Unit, onDelete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(color = accent.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                    Text(
                        text = catatan.nama_kategori?.uppercase() ?: "UMUM",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = accent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, null, tint = Color.LightGray)
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = { expanded = false; onEdit() },
                            leadingIcon = { Icon(Icons.Default.Edit, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Hapus", color = Color.Red) },
                            onClick = { expanded = false; onDelete() },
                            leadingIcon = { Icon(Icons.Default.Delete, null, tint = Color.Red) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = catatan.judul, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = catatan.isi, color = Color.Gray, fontSize = 14.sp, maxLines = 2)
        }
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, label: String, color: Color, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
        Text(label, fontSize = 10.sp, color = color)
    }
}