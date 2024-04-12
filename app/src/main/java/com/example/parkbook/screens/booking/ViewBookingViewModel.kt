//package com.example.parkbook.screens.booking
//
//import android.util.Log
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import com.example.parkbook.data.BookingData
//import com.example.parkbook.navigation.Screen
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//
//class ViewBookingViewModel : ViewModel() {
//    private val _bookings = mutableStateOf<List<Screen.Booking>>(listOf())
//    val bookings: State<List<BookingData>> = _bookings
//
//    init {
//        fetchBookings()
//    }
//    private val TAG = ViewBookingViewModel::class.simpleName
//
//    private fun fetchBookings() {
//        val user = FirebaseAuth.getInstance().currentUser
//        val db = FirebaseFirestore.getInstance()
//
//        user?.let {
//            db.collection("PB_Booking")
//                .whereEqualTo("uid", it.uid)
//                .get()
//                .addOnSuccessListener { result ->
//                    val fetchedBookings = result.documents.mapNotNull { it.toObject(BookingData::class.java) }
//                    _bookings.value = fetchedBookings
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents: ", exception)
//                }
//        }
//    }
//}