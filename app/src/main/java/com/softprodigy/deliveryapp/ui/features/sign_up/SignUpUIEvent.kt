package com.softprodigy.deliveryapp.ui.features.sign_up

sealed class SignUpUIEvent {

    data class Submit(val name: String, val email: String, val password: String) : SignUpUIEvent()

}