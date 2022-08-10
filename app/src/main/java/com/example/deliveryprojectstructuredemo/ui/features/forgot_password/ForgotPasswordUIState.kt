package com.example.deliveryprojectstructuredemo.ui.features.forgot_password

import com.example.deliveryprojectstructuredemo.data.UserInfo

data class ForgotPasswordUIState(
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var user: UserInfo? = null,
    var message: String? = null,
    var isEmailError: Boolean? = null,
)
