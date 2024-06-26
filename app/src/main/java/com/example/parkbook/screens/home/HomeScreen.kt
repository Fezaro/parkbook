package com.example.parkbook.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.parkbook.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,

) {
    val user = FirebaseAuth.getInstance().currentUser
    var fetchedUser by remember { mutableStateOf<HomeViewModel.User?>(null) }
    val TAG = HomeViewModel::class.simpleName

    // Fetch user data when HomeScreen appears and the Firebase user is not null
    LaunchedEffect(key1 = user) {
        user?.let {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("PB_USERS").document(it.uid)
            docRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    fetchedUser = document.toObject(HomeViewModel.User::class.java)
                } else {
                    Log.d(TAG, "No such document")
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "ParkBook",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(start = 16.dp),

                        ) },
                    actions = {
                        Text(text = "Sign Out", style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFFE65100)
                        ), modifier = Modifier.padding(end = 16.dp))
                        IconButton(onClick = {
                            user?.let {
                                FirebaseAuth.getInstance().signOut()
                                navController.navigate(Screen.Login.route)
                            }
                        }) {
                            Icon(Icons.Filled.ExitToApp, contentDescription = "Logout")
                        }
                    }
                )
            },
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome: 👋\n ${fetchedUser?.firstName}" +
                            "${fetchedUser?.lastName}\n",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    ),
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text= "Office: ${fetchedUser?.employer}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                            "Book your parking spot, Hassle Free!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Thin,
                        fontSize = 20.sp
                    ),
                )



                Spacer(modifier = Modifier.height(28.dp))
                BoxWithConstraints {
                    val cardSize = maxWidth / 2
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(cardSize)
                                .clickable { navController.navigate(Screen.Booking.route) },
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            onClick = {
                                navController.navigate(Screen.Booking.route)
                            }

                            ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(
                                                    0xFFFFA726
                                                ), Color(0xFFE65100)
                                            )
                                        )
                                    )
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Book Spot",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(cardSize)
                                .clickable { navController.navigate(Screen.ViewBooking.route) },
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            onClick = {
                                navController.navigate(Screen.ViewBooking.route)
                            }

                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(
                                                    0xFFFFA726
                                                ), Color(0xFFE65100)
                                            )
                                        )
                                    )
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "View Spots",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
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
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController(),

    )
}
