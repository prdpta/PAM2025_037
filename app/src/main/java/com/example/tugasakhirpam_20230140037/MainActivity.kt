package com.example.tugasakhirpam_20230140037

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasakhirpam_20230140037.ui.screens.*
import com.example.tugasakhirpam_20230140037.ui.theme.TugasAkhirPAM_20230140037Theme
import com.example.tugasakhirpam_20230140037.userinterface.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TugasAkhirPAM_20230140037Theme {
                // Menjalankan Navigasi Utama
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // 1. Layar Login
        composable("login") {
            LoginScreen(navController = navController)
        }

        // 2. Layar Home - Mengirimkan userId hasil login
        composable(
            route = "home/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            HomeScreen(navController = navController, userId = userId)
        }

        // 3. Layar Profile
        composable(
            route = "profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            // Pastikan nama fungsinya ProfileScreen (sesuai file yang kita benerin tadi)
            ProfileScreen(navController = navController, userId = userId)
        }

        // 4. Layar Tambah Catatan
        composable(
            route = "add_note/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            AddNoteScreen(navController = navController, userId = userId)
        }

        // 5. Layar Edit Catatan - Menggunakan noteId untuk ambil data spesifik
        composable(
            route = "edit_note/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            EditNoteScreen(navController = navController, noteId = noteId)
        }
    }
}