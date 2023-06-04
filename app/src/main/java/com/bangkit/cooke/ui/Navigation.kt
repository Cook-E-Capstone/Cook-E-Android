package com.bangkit.cooke.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.bangkit.cooke.ui.screens.login.LoginScreen
import com.bangkit.cooke.ui.screens.register.RegisterScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "auth") {
        navigation(
            startDestination = "login",
            route = "auth"
        ) {
            composable("login") {
                // LoginScreen
                LoginScreen(navController = navController)
            }
            composable("register") {
                // RegisterScreen
                RegisterScreen(navController = navController)
            }

        }
    }
}