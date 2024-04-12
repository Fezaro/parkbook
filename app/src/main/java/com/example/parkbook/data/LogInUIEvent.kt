package com.example.parkbook.data

sealed class LogInUIEvent {
    data class loginEmailChanged(val email: String) : LogInUIEvent()
    data class loginPasswordChanged(val password: String) : LogInUIEvent()

    object loginButtonClicked : LogInUIEvent()




}