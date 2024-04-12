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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
//fun ViewBookingScreen(
//    navController: NavController,
//){
//    val user = FirebaseAuth.getInstance().currentUser
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text(text = "ParkBook") },
//                    actions = {
//                        IconButton(onClick = {
//                            user?.let {
//                                FirebaseAuth.getInstance().signOut()
//                                navController.navigate(Screen.Login.route)
//                            }
//                        }) {
//                            Icon(Icons.Filled.ExitToApp, contentDescription = "Logout")
//                        }
//                    }
//                )
//            },
//        ) { contentPadding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(contentPadding),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(text = "Booking View Screen")
//            }
//        }
//    }
//}

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
                    bookings = fetchedBookings
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
                    title = { Text(text = "ParkBook") },
                    actions = {
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
                BookingsList(bookings)
            }
        }
    }
}

//fun ViewBookingScreen(
//    navController: NavController,
//){
//    val user = FirebaseAuth.getInstance().currentUser
//    val db = FirebaseFirestore.getInstance()
//    var bookings by remember { mutableStateOf<List<BookingData>>(listOf()) }
//    val TAG = "VIEW BOOKINGS"
//
//    LaunchedEffect(key1 = user) {
////        Log.d()
////        val bookings
//        fetchBookings(user, bookings)
//    }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text(text = "ParkBook") },
//                    actions = {
//                        IconButton(onClick = {
//                            user?.let {
//                                FirebaseAuth.getInstance().signOut()
//                                navController.navigate(Screen.Login.route)
//                            }
//                        }) {
//                            Icon(Icons.Filled.ExitToApp, contentDescription = "Logout")
//                        }
//                    }
//                )
//            },
//        ) { contentPadding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(contentPadding),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                if (bookings.isEmpty()) {
//                    Text(text = "No bookings available")
//                } else {
//                    BookingsList(bookings)
//                }
//            }
//        }
//    }
//}

@Composable
fun BookingsList(bookings: List<BookingData>, snackbarHostState: SnackbarHostState) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = bookings) { booking ->
            BookingItem(booking, snackbarHostState)
        }
    }
}

@Composable
fun BookingItem(
    booking: BookingData,
    snackbarHostState: SnackbarHostState
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

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
//                Text(text = "Office: ${booking.employer}")
//                Text(text = "Booking Date: ${booking.bookingDate}")
//                Text(text = "From: ${booking.bookingFromTime}")
//                Text(text = "To: ${booking.bookingToTime}")
//                Text(text = "Spot: ${booking.bookingSpot}")
                Text(
                    text = "Office: ${booking.employer}",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
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
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp) )

                Text(
                    text = "From: ${booking.bookingFromTime}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp) )

                Text(
                    text = "To: ${booking.bookingToTime}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (expanded) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ElevatedButton(
                        onClick = { deleteBooking(booking.uid) },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )

                    ) {
                        Text("Delete Booking")
                    }
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
//fun fetchBookings(user: FirebaseUser?, bookings: List<BookingData>) {
//
//    val db = FirebaseFirestore.getInstance()
//    user?.let {
//        db.collection("PB_Booking")
//            .whereEqualTo("uid", it.uid)
//            .get()
//            .addOnSuccessListener { result ->
//                val fetchedBookings = result.documents.mapNotNull { it.toObject(BookingData::class.java) }
//                Log.d("Fetch Bookings", "Fetched bookings: $fetchedBookings")
//                bookings.value = fetchedBookings
//            }
//            .addOnFailureListener { exception ->
//                Log.w("Fetch Bookings", "Error getting documents: ", exception)
//            }
//    }
//}
fun deleteBooking(uid: String) {
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

//
//@Composable
//private fun Greetings(
//    modifier: Modifier = Modifier,
//    names: List<String> = List(1000) { "$it" } // here $it represents the list index)
//) {
//    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
//        items(items = names) { name ->
//            Greeting(name = name)
//        }
//    }
//}
//
//@Composable
//private fun Greeting(name: String, modifier: Modifier = Modifier) {
//
//    var expanded by rememberSaveable { mutableStateOf(false) }
//
//    val extraPadding by animateDpAsState(
//        if (expanded) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
//    Surface(
//        color = MaterialTheme.colorScheme.primary,
//        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//    ) {
//        Row(modifier = Modifier.padding(24.dp)) {
//            Column(modifier = Modifier
//                .weight(1f)
//                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
//            ) {
//                Text(text = "Hello, ")
//                Text(text = name, style = MaterialTheme.typography.headlineMedium.copy(
//                    fontWeight = FontWeight.ExtraBold
//                )
//                )
//            }
//            ElevatedButton(
//                onClick = { expanded = !expanded }
//            ) {
//                Text(if (expanded) "Show less" else "Show more")
//            }
//
//        }
//    }
//}