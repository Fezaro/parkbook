package com.example.parkbook


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable

import androidx.compose.ui.tooling.preview.Preview

import com.example.parkbook.navigation.ParkBookNavigation
import com.example.parkbook.screens.login.LoginScreen

import com.example.parkbook.ui.theme.ParkbookTheme
import androidx.navigation.compose.rememberNavController


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ParkBookApp() {
    val navController = rememberNavController()

    ParkbookTheme {
        // A surface container using the 'background' color from the theme
        LoginScreen(navController)
        ParkBookNavigation(navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showBackground = true,
    widthDp = 380,
    heightDp = 800
    )
@Composable
fun DefaultPreview() {
    ParkbookTheme {

        ParkBookApp()
    }
}

//@Composable
//fun rememberAppState(
//    scaffoldState: ScaffoldState = rememberScaffoldState(),
//    navController: NavHostController = rememberNavController(),
//    snackbarManager: SnackbarManager = SnackbarManager,
//    resources: Resources = resources(),
//    coroutineScope: CoroutineScope = rememberCoroutineScope()
//) =
//    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
//        MakeItSoAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
//    }