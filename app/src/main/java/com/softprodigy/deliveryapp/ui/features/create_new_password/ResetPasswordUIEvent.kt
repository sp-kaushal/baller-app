package com.softprodigy.deliveryapp.ui.features.create_new_password


sealed class ResetPasswordUIEvent {

    data class PasswordChange(val password: String) : ResetPasswordUIEvent()
    data class PasswordToggleChange(val showPassword: Boolean) : ResetPasswordUIEvent()
    data class ConfirmPasswordChange(val password: String) : ResetPasswordUIEvent()
    data class ConfirmPasswordToggleChange(val showPassword: Boolean) : ResetPasswordUIEvent()
    object Submit : ResetPasswordUIEvent()

}