
package com.neu.mobileapplicationdevelopment202430.navigation

// A sealed class to define app navigation routes
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object ProductList : Screen("product_list")
}
