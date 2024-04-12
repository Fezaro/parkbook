package com.example.parkbook.data

sealed class SignUpUIEvent {
    data class signUpEmployerChanged(val employer: String) : SignUpUIEvent()
    data class signUpFirstNameChanged(val firstName: String) : SignUpUIEvent()
    data class signUpLastNameChanged(val lastName: String) : SignUpUIEvent()
    data class signUpEmailChanged(val email: String) : SignUpUIEvent()
    data class signUpPasswordChanged(val password: String) : SignUpUIEvent()
    data class signUpConfirmPasswordChanged(val confirmPassword: String) : SignUpUIEvent()



    object signUpButtonClicked : SignUpUIEvent()


}