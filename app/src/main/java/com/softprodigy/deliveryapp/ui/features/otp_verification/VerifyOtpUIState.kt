package com.example.deliveryprojectstructuredemo.ui.features.otp_verification

import com.example.deliveryprojectstructuredemo.data.UserInfo

data class VerifyOtpUIState(
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var user: UserInfo? = null,
    var message: String? = null,
    var isOtpError: Boolean? = null,
)