//package screens.login
//
//import androidx.compose.runtime.mutableStateOf
//import com.example.parkbook.common.snackbar.SnackbarManager
//import dagger.hilt.android.lifecycle.HiltViewModel
//import com.example.parkbook.R.string as AppText
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
