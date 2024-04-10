package com.example.parkbook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import com.example.parkbook.screens.Signup.SignUpScreen
import com.example.parkbook.screens.login.LoginScreen

@Composable
fun ParkBookNavigation(
    navController: NavHostController,

){

    NavHost(navController = navController, startDestination = Screen.Login.route){
        composable(Screen.Login.route){
             LoginScreen(navController = navController)
        }
        composable("signUp"){
             SignUpScreen(navController = navController)
        }
        composable("home"){
            // ParkHomeScreen(navController = navController)
        }
    }
}