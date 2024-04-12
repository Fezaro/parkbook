package com.example.parkbook.screens.Signup

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.parkbook.common.ext.isValidEmail
import com.example.parkbook.data.SignUpUIEvent
import com.example.parkbook.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    var snackbarHostState = SnackbarHostState()
    var scope: CoroutineScope? = null
    private val signUpUIState = mutableStateOf(SignUpUiState())
    // add navController instance
    lateinit var navController: NavController

    var signUpInprogress = mutableStateOf(false)


    private val TAG = SignUpViewModel::class.simpleName


    private fun printState() {
        Log.d(TAG, "signUpUIState: ${signUpUIState.value}")
    }

    fun onEvent(event: SignUpUIEvent) {


        when (event) {
            is SignUpUIEvent.signUpEmployerChanged -> {
                signUpUIState.value = signUpUIState.value.copy(employer = event.employer)
                printState()
            }

            is SignUpUIEvent.signUpFirstNameChanged -> {
                signUpUIState.value = signUpUIState.value.copy(firstName = event.firstName)
                printState()
            }

            is SignUpUIEvent.signUpLastNameChanged -> {
                signUpUIState.value = signUpUIState.value.copy(lastName = event.lastName)
                printState()
            }

            is SignUpUIEvent.signUpEmailChanged -> {
                signUpUIState.value = signUpUIState.value.copy(email = event.email)

                printState()
            }

            is SignUpUIEvent.signUpPasswordChanged -> {
                signUpUIState.value = signUpUIState.value.copy(password = event.password)
                printState()
            }

            is SignUpUIEvent.signUpConfirmPasswordChanged -> {
                signUpUIState.value =
                    signUpUIState.value.copy(confirmPassword = event.confirmPassword)
                printState()
            }

            is SignUpUIEvent.signUpButtonClicked -> {
                signUp()
            }


        }
    }

    private fun signUp() {
//        TODO("Not yet implemented")
        // validations
        if (!signUpUIState.value.email.isValidEmail()) {
            scope?.launch {
                snackbarHostState?.showSnackbar("Invalid email")
            }
            return
        }

        if (signUpUIState.value.password.isBlank()) {
            scope?.launch {
                snackbarHostState?.showSnackbar("Password cannot be empty")
            }

            return
        }

        if (signUpUIState.value.confirmPassword.isBlank()) {
            scope?.launch {
                snackbarHostState?.showSnackbar("Confirm Password cannot be empty")
            }

            return
        }

        if (signUpUIState.value.password != signUpUIState.value.confirmPassword) {
            scope?.launch {
                snackbarHostState?.showSnackbar("Passwords do not match")
            }

            return
        }
        if (signUpUIState.value.employer.isBlank()) {
            scope?.launch {
                snackbarHostState?.showSnackbar("Employer cannot be empty")
            }

            return
        }

        if (signUpUIState.value.firstName.isBlank()) {
            scope?.launch {
                snackbarHostState?.showSnackbar("First Name cannot be empty")
            }

            return
        }

        if (signUpUIState.value.lastName.isBlank()) {
            scope?.launch {
                snackbarHostState?.showSnackbar("Last Name cannot be empty")
            }

            return
        }

        createUserInFirebase(

            signUpUIState.value.email,
            signUpUIState.value.password
        )


        Log.d(TAG, "SignUp: ${signUpUIState.value}")
    }

    // add firebase create user func
    private fun createUserInFirebase(email: String, password: String) {
        signUpInprogress.value = true
        // create user in firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    addUserToFirestore()
                    Log.d(TAG, "createUserInFirebase completed: success")
                    signUpInprogress.value = false

                    // add snackbar
                    scope?.launch {
                        snackbarHostState?.showSnackbar("User created successfully")
                    }

                    //Navigate to login screen

                    navController.navigate(Screen.Login.route)
                } else {
                    signUpInprogress.value = false
                    Log.d(TAG, "createUserInFirebase completed: failed")
                    // add snackbar
                    scope?.launch {
                        snackbarHostState?.showSnackbar("Failed to create user")
                    }
                }
            }
            .addOnFailureListener{
                signUpInprogress.value = false
                Log.d(TAG, "createUserInFirebase: failed")
                Log.d(TAG, "Exception: ${it.message} ")

                // add snackbar
                scope?.launch {
                    snackbarHostState?.showSnackbar("Failed to create user")
                }


            }


    }

    private fun addUserToFirestore() {
        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        val userData = hashMapOf(
            "uid" to user?.uid,
            "userEmail" to signUpUIState.value.email,
            "employer" to signUpUIState.value.employer,
            "firstName" to signUpUIState.value.firstName,
            "lastName" to signUpUIState.value.lastName
        )

        user?.let {
            db.collection("PB_USERS")
                .document(it.uid)
                .set(userData)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error writing document", e)
                }
        }
    }
}