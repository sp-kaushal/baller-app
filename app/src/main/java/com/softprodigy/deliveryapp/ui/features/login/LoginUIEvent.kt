package com.softprodigy.deliveryapp.ui.features.login

import com.softprodigy.deliveryapp.data.GoogleUserModel
sealed class LoginUIEvent {
    data class Submit(val email: String, val password: String) : LoginUIEvent()
    data class OnGoogleClick(val googleUser: GoogleUserModel) : LoginUIEvent()

}