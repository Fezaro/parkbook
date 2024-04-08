package com.example.parkbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkbook.components.ClickableRedirectText
import com.example.parkbook.components.ParkTextbox
import com.example.parkbook.ui.theme.BrightOrange
import com.example.parkbook.ui.theme.Chartreuse
import com.example.parkbook.ui.theme.Navy
import com.example.parkbook.ui.theme.ParkbookTheme
import com.example.parkbook.ui.theme.Purple80
import com.example.parkbook.ui.theme.PurpleGrey40

@Composable
fun ParkBookApp(){
    ParkbookTheme {
        // A surface container using the 'background' color from the theme
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
                        modifier = Modifier)

                    Text(
                        text = "Welcome",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ))
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

                ClickableRedirectText(labelText = "Are you new here? ", linkText = "Sign Up") {
                    
                }


            }
        }
    }
}

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