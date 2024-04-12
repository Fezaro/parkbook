package com.example.parkbook.screens.booking

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.parkbook.data.BookingUIEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {
    var snackbarHostState = SnackbarHostState()
    var scope: CoroutineScope? = null
    private val bookingUIState = mutableStateOf(BookingUiState())
    lateinit var navController: NavController
    private val TAG = BookingViewModel::class.simpleName

    private fun printState(){
        Log.d(TAG, "BookingUIState: ${bookingUIState.value}")
    }

    fun onEvent(event: BookingUIEvent) {
        when(event) {
            is BookingUIEvent.bookingEmployerChanged -> {
                bookingUIState.value = bookingUIState.value.copy(
                    employer = event.employer
                )
                printState()
            }
            is BookingUIEvent.bookingDateChanged -> {
                bookingUIState.value = bookingUIState.value.copy(
                    bookingDate = event.bookingDate)
                printState()
            }
            is BookingUIEvent.bookingFromTimeChanged -> {
                bookingUIState.value = bookingUIState.value.copy(
                    bookingFromTime = event.bookingFromTime)
                printState()
            }
            is BookingUIEvent.bookingToTimeChanged -> {
                bookingUIState.value = bookingUIState.value.copy(
                    bookingToTime = event.bookingToTime)
                printState()
            }
            is BookingUIEvent.bookingSpotChanged -> {
                bookingUIState.value = bookingUIState.value.copy(
                    bookingSpot = event.bookingSpot)
                printState()
            }

            is BookingUIEvent.bookingButtonClicked ->{
                bookSpot()
            }
        }
    }

    private fun bookSpot() {
//        TODO("Not yet implemented")
        // validation
        if (bookingUIState.value.employer.isBlank()) {
            scope?.launch {
                snackbarHostState.showSnackbar("Employer cannot be Empty.")
            }

            return
        }

        if (bookingUIState.value.bookingDate.isBlank()) {
            scope?.launch {
                snackbarHostState.showSnackbar("Employer cannot be Empty.")
            }
            return
        }

        if (bookingUIState.value.bookingFromTime.isBlank()) {
            scope?.launch {
                snackbarHostState.showSnackbar("Employer cannot be Empty.")
            }
            return
        }

        if (bookingUIState.value.bookingToTime.isBlank()) {
            scope?.launch {
                snackbarHostState.showSnackbar("Employer cannot be Empty.")
            }
            return
        }


        Log.d(TAG, "Booking: ${bookingUIState.value}")

        addBookingToFirestore()


    }

    private fun addBookingToFirestore() {
        // add booking to firestore PB_Booking collection
        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        val booking = hashMapOf(
            "employer" to bookingUIState.value.employer,
            "bookingDate" to bookingUIState.value.bookingDate,
            "bookingFromTime" to bookingUIState.value.bookingFromTime,
            "bookingToTime" to bookingUIState.value.bookingToTime,
            "bookingSpot" to bookingUIState.value.bookingSpot
        )

        user?.let {
            db.collection("PB_Booking")
                .document(it.uid)
                .set(booking)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error writing document", e)
                }
        }

    }

}