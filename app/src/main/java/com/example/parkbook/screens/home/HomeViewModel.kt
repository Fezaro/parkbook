package com.example.parkbook.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val TAG = HomeViewModel::class.simpleName

    data class User (
        val uid: String = "",
        val userEmail: String = "",
        val employer: String = "",
        val firstName: String = "",
        val lastName: String = ""
    )

    // MutableStateFlow, LiveData or State can be used here depending on your use case
    val user = mutableStateOf<User?>(null)

//    fun fetchUser(uid: String) {
//        viewModelScope.launch {
//            val docRef = db.collection("PB_USERS").document(uid)
//            docRef.get().addOnSuccessListener { document ->
//                if (document != null) {
//                    val fetchedUser = document.toObject(User::class.java)
//                    user.value = fetchedUser
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }.addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
//        }
//    }
}