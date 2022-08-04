package com.example.deliveryprojectstructuredemo.ui.features.login


sealed class LoginUIEvent {
    data class EmailChange(val email: String) : LoginUIEvent()
    data class PasswordChange(val password: String) : LoginUIEvent()
    data class PasswordToggleChange(val showPassword: Boolean) : LoginUIEvent()
    data class EmailError(val isEmailError: Boolean) : LoginUIEvent()
    data class PasswordError(val isPasswordError: Boolean) : LoginUIEvent()
    object Submit : LoginUIEvent()
}