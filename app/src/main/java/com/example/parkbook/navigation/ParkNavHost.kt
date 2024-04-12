package com.example.parkbook.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parkbook.screens.Signup.SignUpScreen
import com.example.parkbook.screens.booking.BookingScreen
import com.example.parkbook.screens.booking.ViewBookingScreen
import com.example.parkbook.screens.home.HomeScreen
import com.example.parkbook.screens.login.LoginScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ParkBookNavigation(
    navController: NavHostController,

){

    NavHost(navController = navController, startDestination = Screen.Login.route){
        composable(Screen.Login.route){
             LoginScreen(navController = navController)
        }
        composable(Screen.SignUp.route){
             SignUpScreen(navController = navController)
        }
        composable(Screen.Home.route){
            HomeScreen(navController = navController)
        }
        composable(Screen.Booking.route){
            BookingScreen(navController = navController)
        }
        composable(Screen.ViewBooking.route){
            ViewBookingScreen(navController = navController )
        }
    }
}