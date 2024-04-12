package com.example.parkbook.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signUp")
    object Home : Screen("home")

    object Booking : Screen("booking")

    object ViewBooking : Screen("viewbooking")
}