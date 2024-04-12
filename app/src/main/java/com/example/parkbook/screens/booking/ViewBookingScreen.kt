package com.example.parkbook.screens.booking

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.parkbook.data.BookingData
import com.example.parkbook.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewBookingScreen(
    navController: NavController,
){
    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()
    var bookings by remember { mutableStateOf<List<BookingData>>(listOf()) }
    val TAG = "--VIEW BOOKINGS--"
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = user) {
        user?.let {
            db.collection("PB_Booking")
                .whereEqualTo("uid", it.uid)
                .get()
                .addOnSuccessListener { result ->
                    val fetchedBookings = result.documents.mapNotNull { it.toObject(BookingData::class.java) }
                    Log.d(TAG, "Fetched Bookings: $fetchedBookings")
                    bookings = fetchedBookings
                    Log.d(TAG, "Bookings: $bookings")
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
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
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                BookingsList(bookings)
                if (bookings.isEmpty()) {
                    Text("No parking Spot booked")
                } else {
                    BookingsList(bookings, snackbarHostState,navController )
                }
            }
        }
    }
}

@Composable
fun BookingsList(bookings: List<BookingData>, snackbarHostState: SnackbarHostState, navController: NavController) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = bookings) { booking ->
            BookingItem(booking, snackbarHostState, navController)
        }
    }
}

@Composable
fun BookingItem(
    booking: BookingData,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {

                Text(
                    text = "Office: ${booking.employer}",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Light),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(2.dp) )
                Text(
                    text = "Spot: ${booking.bookingSpot}",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(2.dp) )

                Text(
                    text = "Booking Date: ${booking.bookingDate}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,

                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp) )

                Text(
                    text = "From: ${booking.bookingFromTime}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,

                        ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp) )

                Text(
                    text = "To: ${booking.bookingToTime}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,

                        ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (expanded) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ElevatedButton(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text(
                            "Delete Booking",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Confirm Delete") },
                        text = { Text("Are you sure you want to delete this booking?") },
                        confirmButton = {
                            TextButton(
                                onClick = {

                                    deleteBooking(booking.uid, snackbarHostState)
                                    showDialog = false

                                    // navigate to view bookings
                                    navController.navigate(Screen.ViewBooking.route)

                                }
                            ) {
                                Text("Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showDialog = false }
                            ) {
                                Text("Cancel")
                            }
                        }
                    )
                }

            }
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}

fun deleteBooking(
    uid: String,
    snackbarHostState: SnackbarHostState,


){
    val db = FirebaseFirestore.getInstance()
    db.collection("PB_Booking")
        .document(uid)
        .delete()
        .addOnSuccessListener {
            Log.d("Delete Booking", "DocumentSnapshot successfully deleted!")

            CoroutineScope(Dispatchers.Main).launch {
                snackbarHostState.showSnackbar("Booking deleted successfully")
            }}
        .addOnFailureListener { e ->
            Log.w("Delete Booking", "Error deleting document", e)
            CoroutineScope(Dispatchers.Main).launch {
                snackbarHostState.showSnackbar("Booking deletion failed")
            }
        }
}
