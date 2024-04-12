package com.example.parkbook.screens.login

import LoginViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.parkbook.R
import com.example.parkbook.components.ClickableRedirectText
import com.example.parkbook.components.ParkTextbox
import com.example.parkbook.data.LogInUIEvent
import com.example.parkbook.ui.theme.Chartreuse
import com.example.parkbook.ui.theme.Navy



@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    loginViewModel.snackbarHostState = snackbarHostState
    loginViewModel.scope = scope
    loginViewModel.navController = navController

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
//            floatingActionButton = {
//                ExtendedFloatingActionButton(
//                    text = { Text("Show snackbar") },
//                    icon = { Icon(Icons.Filled.Build, contentDescription = "") },
//                    onClick = {
//                        scope.launch {
//                            snackbarHostState.showSnackbar("Snackbar")
//                        }
//                    }
//                )
//            }
            ) { contentPadding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Image(
                        painter = painterResource(id = R.drawable.parkapp_icon),
                        contentDescription = "parkbook app logo ",
                        modifier = Modifier
                    )

                    Text(
                        text = "Welcome",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Login to book a parking spot.")

                    Spacer(modifier = Modifier.height(16.dp))
                    ParkTextbox("Email", onTextChanged = {
                        loginViewModel.onEvent(LogInUIEvent.loginEmailChanged(it))

                    })



                    Spacer(modifier = Modifier.height(16.dp))
                    ParkTextbox("Password", onTextChanged = {
                        loginViewModel.onEvent(LogInUIEvent.loginPasswordChanged(it))
                    })

                    Spacer(modifier = Modifier.height(20.dp))

                    ElevatedButton(
                        onClick = {
                            loginViewModel.onEvent(LogInUIEvent.loginButtonClicked)
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Chartreuse,
                            contentColor = Navy
                        )

                    ) {

                        Text(text = "Login", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    ClickableRedirectText(
                        labelText = "Are you new here? ",
                        linkText = "Sign Up",
                        navController = navController,
                        navRoutes = mapOf("Sign Up" to "signUp")
                    )

                }


            }

        }
    if (loginViewModel.logInprogress.value){
        CircularProgressIndicator()
    }
    }
}