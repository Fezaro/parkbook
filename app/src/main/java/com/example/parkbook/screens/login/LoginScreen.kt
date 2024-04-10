package com.example.parkbook.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.parkbook.R
import com.example.parkbook.components.ClickableRedirectText
import com.example.parkbook.components.ParkTextbox
import com.example.parkbook.ui.theme.Chartreuse
import com.example.parkbook.ui.theme.Navy

@Composable
fun LoginScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
//            val appState = rememberAppState()
//            Scaffold {
//                ParkBookScreen(appState)
//            }
        Column(
            modifier = Modifier.fillMaxSize(),
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
            ParkTextbox("Email")

            Spacer(modifier = Modifier.height(16.dp))
            ParkTextbox("Password")

            Spacer(modifier = Modifier.height(20.dp))

            ElevatedButton(
                onClick = { /*TODO*/ },
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