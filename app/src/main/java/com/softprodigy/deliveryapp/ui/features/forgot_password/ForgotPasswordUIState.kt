package com.softprodigy.deliveryapp.ui.features.forgot_password

import com.softprodigy.deliveryapp.data.UserInfo


data class ForgotPasswordUIState(
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var user: UserInfo? = null,
    var message: String? = null,
    var isEmailError: Boolean? = null,
)
