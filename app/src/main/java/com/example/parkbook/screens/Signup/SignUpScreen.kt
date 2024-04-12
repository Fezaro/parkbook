package com.example.parkbook.screens.Signup

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.parkbook.R
import com.example.parkbook.components.ClickableRedirectText
import com.example.parkbook.components.ParkTextbox
import com.example.parkbook.data.SignUpUIEvent
import com.example.parkbook.ui.theme.Chartreuse
import com.example.parkbook.ui.theme.Navy

@Composable
fun SignUpScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    signUpViewModel.snackbarHostState = snackbarHostState
    signUpViewModel.scope = scope
    signUpViewModel.navController = navController
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
            ){
                    contentPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.parkapp_icon),
                        contentDescription = "parkbook app logo ",
                        modifier = Modifier
                    )
                    Text(
                        text = " Create your account",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    ParkTextbox(
                        "Employer",
                        onTextChanged = {
                            signUpViewModel.onEvent(SignUpUIEvent.signUpEmployerChanged(it))

                        }
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    ParkTextbox(
                        "First Name",
                        onTextChanged = {
                            signUpViewModel.onEvent(SignUpUIEvent.signUpFirstNameChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    ParkTextbox(
                        "Last Name",
                        onTextChanged = {
                            signUpViewModel.onEvent(SignUpUIEvent.signUpLastNameChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    ParkTextbox(
                        "Email",
                        onTextChanged = {
                            signUpViewModel.onEvent(SignUpUIEvent.signUpEmailChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    ParkTextbox(
                        "Password",
                        onTextChanged = {
                            signUpViewModel.onEvent(SignUpUIEvent.signUpPasswordChanged(it))
                        }

                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    ParkTextbox(
                        "Confirm Password",
                        onTextChanged = {
                            signUpViewModel.onEvent(SignUpUIEvent.signUpConfirmPasswordChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ElevatedButton(
                        onClick = {
                            signUpViewModel.onEvent(SignUpUIEvent.signUpButtonClicked)
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Chartreuse,
                            contentColor = Navy
                        )

                    ) {

                        Text(text = "Sign Up", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ClickableRedirectText(
                        labelText = "Already have an account? ",
                        linkText = "Log in",
                        navRoutes = mapOf("Log in" to "login"),
                        navController = navController
                    )
                }
            }
        }

        if(signUpViewModel.signUpInprogress.value){
            CircularProgressIndicator()
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 380,
    heightDp = 800
)
@Composable
fun SignUpPreview() {

    SignUpScreen(navController = rememberNavController())
}


