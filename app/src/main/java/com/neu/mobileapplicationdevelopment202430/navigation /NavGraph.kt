package com.neu.mobileapplicationdevelopment202430.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.neu.mobileapplicationdevelopment202430.navigation.Screen
import com.neu.mobileapplicationdevelopment202430.ui.login.LoginScreen
import com.neu.mobileapplicationdevelopment202430.ui.product.ProductListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            // Navigate to Product List on successful login
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.ProductList.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        composable(Screen.ProductList.route) {
            ProductListScreen()
        }
    }
}
