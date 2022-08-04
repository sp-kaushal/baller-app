package com.example.deliveryprojectstructuredemo.ui.features.login

import com.example.deliveryprojectstructuredemo.data.UserInfo


data class LoginUIState(
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var user: UserInfo? = null,
    var isEmailError: Boolean? = null,
    var isPasswordError: Boolean? = null,
)
