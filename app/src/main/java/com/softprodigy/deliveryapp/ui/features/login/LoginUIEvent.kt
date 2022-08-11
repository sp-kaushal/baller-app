package com.softprodigy.deliveryapp.ui.features.login


sealed class LoginUIEvent {
    data class Submit(val email: String,val password: String) : LoginUIEvent()
}