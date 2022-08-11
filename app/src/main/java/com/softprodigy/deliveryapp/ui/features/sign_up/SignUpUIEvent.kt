package com.softprodigy.deliveryapp.ui.features.sign_up

sealed class SignUpUIEvent {
    data class NameChange(val name: String) : SignUpUIEvent()
    data class EmailChange(val email: String) : SignUpUIEvent()
    data class PasswordChange(val password: String) : SignUpUIEvent()
    data class PasswordToggleChange(val showPassword: Boolean) : SignUpUIEvent()
    data class ConfirmTermsChange(val acceptTerms: Boolean) : SignUpUIEvent()
    object Submit : SignUpUIEvent()
}