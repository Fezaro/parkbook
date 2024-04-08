package com.example.parkbook.screens.Signup

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkbook.R
import com.example.parkbook.components.ClickableRedirectText
import com.example.parkbook.components.ParkTextbox
import com.example.parkbook.ui.theme.Chartreuse
import com.example.parkbook.ui.theme.Navy

@Composable
fun SignUpScreen(){
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.parkapp_icon),
                contentDescription = "parkbook app logo ",
                modifier = Modifier) 
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            ParkTextbox("Employer")
            Spacer(modifier = Modifier.height(2.dp))
            ParkTextbox("First Name")
            Spacer(modifier = Modifier.height(2.dp))
            ParkTextbox("Last Name")
            Spacer(modifier = Modifier.height(2.dp))
            ParkTextbox("Email")
            Spacer(modifier = Modifier.height(2.dp))
            ParkTextbox("Password")
            Spacer(modifier = Modifier.height(2.dp))
            ParkTextbox("Confirm Password")
            Spacer(modifier = Modifier.height(2.dp))
            ElevatedButton(
                onClick = { /*TODO*/ },
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
                onClick = { /*TODO*/ }
            )
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

    SignUpScreen()
}