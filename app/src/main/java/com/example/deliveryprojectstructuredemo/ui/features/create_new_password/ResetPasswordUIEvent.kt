package com.example.deliveryprojectstructuredemo.ui.features.create_new_password

import com.example.deliveryprojectstructuredemo.ui.features.login.LoginUIEvent

sealed class ResetPasswordUIEvent {

    data class PasswordChange(val password: String) : ResetPasswordUIEvent()
    data class PasswordToggleChange(val showPassword: Boolean) : ResetPasswordUIEvent()
    data class ConfirmPasswordChange(val password: String) : ResetPasswordUIEvent()
    data class ConfirmPasswordToggleChange(val showPassword: Boolean) : ResetPasswordUIEvent()
    object Submit : ResetPasswordUIEvent()

}