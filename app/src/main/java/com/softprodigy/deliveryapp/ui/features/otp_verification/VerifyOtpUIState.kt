package com.softprodigy.deliveryapp.ui.features.otp_verification

import com.softprodigy.deliveryapp.data.UserInfo


data class VerifyOtpUIState(
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var user: UserInfo? = null,
    var message: String? = null,
    var isOtpError: Boolean? = null,
)