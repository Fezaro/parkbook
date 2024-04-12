import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.parkbook.common.ext.isValidEmail
import com.example.parkbook.data.LogInUIEvent
import com.example.parkbook.screens.login.LoginUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//package screens.login
//
//import androidx.compose.runtime.mutableStateOf
//import com.example.parkbook.common.snackbar.SnackbarManager
//import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.parkbook.R.string as AppText
//import javax.inject.Inject
//
//@HiltViewModel
//class LoginViewModel @Inject constructor(
//    private val accountService: AccountService,
//    logService: LogService
//) : MakeItSoViewModel(logService) {
//    var uiState = mutableStateOf(LoginUiState())
//        private set
//
//    private val email
//        get() = uiState.value.email
//    private val password
//        get() = uiState.value.password
//
//    fun onEmailChange(newValue: String) {
//        uiState.value = uiState.value.copy(email = newValue)
//    }
//
//    fun onPasswordChange(newValue: String) {
//        uiState.value = uiState.value.copy(password = newValue)
//    }
//
//    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
//        if (!email.isValidEmail()) {
//            SnackbarManager.showMessage(AppText.email_error)
//            return
//        }
//
//        if (password.isBlank()) {
//            SnackbarManager.showMessage(AppText.empty_password_error)
//            return
//        }
//
//        launchCatching {
//            accountService.authenticate(email, password)
//            openAndPopUp(SETTINGS_SCREEN, LOGIN_SCREEN)
//        }
//    }
//
//    fun onForgotPasswordClick() {
//        if (!email.isValidEmail()) {
//            SnackbarManager.showMessage(AppText.email_error)
//            return
//        }
//
//        launchCatching {
//            accountService.sendRecoveryEmail(email)
//            SnackbarManager.showMessage(AppText.recovery_email_sent)
//        }
//    }
//}

class LoginViewModel : ViewModel() {
    var snackbarHostState = SnackbarHostState()
    var scope: CoroutineScope? = null
    var loginUIState = mutableStateOf(LoginUiState())

    private val TAG = LoginViewModel::class.simpleName
    lateinit var navController: NavController
    var logInprogress = mutableStateOf(false)

    private fun printState() {
        Log.d(TAG, "loginUIState: ${loginUIState.value.toString()}")
    }



    fun onEvent(event: LogInUIEvent) {
        when (event) {
            is LogInUIEvent.loginEmailChanged -> {
                loginUIState.value = loginUIState.value.copy(email = event.email)
                printState()
            }
            is LogInUIEvent.loginPasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(password = event.password)
                printState()
            }
            is LogInUIEvent.loginButtonClicked -> {
                login()
            }

            else -> {}
        }
    }

    private fun login() {
//        TODO("Not yet implemented")
        // Validate the email and password
        if (!loginUIState.value.email.isValidEmail()) {
            scope?.launch {
                snackbarHostState?.showSnackbar("Invalid email")
            }
            return
        }

        if (loginUIState.value.password.isBlank()) {
            scope?.launch {
                snackbarHostState?.showSnackbar("Password cannot be empty")
            }

            return
        }

        signInUserfirebase(loginUIState.value.email, loginUIState.value.password)



        Log.d(TAG, "Login: ${loginUIState.value}")
    }

    private fun signInUserfirebase(
        email: String,
        password: String
    ) {
        logInprogress.value = true
        // Firebase authentication
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    logInprogress.value = false
                    Log.d(TAG, "signInUserfirebase Completed: success")
                    navController.navigate("home")
                } else {
                    logInprogress.value = false

                    Log.d(TAG, "signInUserfirebase Completed: failed")
                    scope?.launch {
                        snackbarHostState?.showSnackbar("Login failed")
                    }
                }
            }
            .addOnFailureListener {
                logInprogress.value = false
                Log.d(TAG, "signInUserfirebase: failed")
                scope?.launch {
                    snackbarHostState?.showSnackbar("Login failed")
                }
            }

    }


}