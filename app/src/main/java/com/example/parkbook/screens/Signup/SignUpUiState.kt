package com.example.parkbook.screens.Signup

data class SignUpUiState(
    val employer: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)