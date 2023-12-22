package com.example.buzzz.ui.theme

// NavGraph.kt

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.buzzz.SplashScreen1
import com.example.buzzz.SplashScreen2
import com.example.buzzz.SplashScreen3

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splashScreen1"
    ) {
        composable("splashScreen1") {
            SplashScreen1(navController)
        }
        composable("splashScreen2") {
            SplashScreen2(navController)
        }
        composable("splashScreen3") {
            SplashScreen3(navController)
        }
        composable("login"){
            LoginForm(navController)
        }
        composable("signup"){
            SignUpForm(navController)
        }
        composable("home"){
            PhotoListScreen(navController)
        }
        composable("groupChat")
        {
            ChatScreen()
        }


    }
}
