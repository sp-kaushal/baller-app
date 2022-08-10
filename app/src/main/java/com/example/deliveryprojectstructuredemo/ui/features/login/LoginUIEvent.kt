package com.example.deliveryprojectstructuredemo.ui.features.login


sealed class LoginUIEvent {
    data class Submit(val email: String,val password: String) : LoginUIEvent()
}