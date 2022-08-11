package com.example.deliveryprojectstructuredemo.ui.features.forgot_password


sealed class ForgotPasswordUIEvent {
    data class EmailChange(val email: String) : ForgotPasswordUIEvent()
    data class EmailError(val isEmailError: Boolean) : ForgotPasswordUIEvent()
    object Submit : ForgotPasswordUIEvent()
}