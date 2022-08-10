package com.example.deliveryprojectstructuredemo.ui.features.otp_verification

import com.example.deliveryprojectstructuredemo.ui.features.forgot_password.ForgotPasswordUIEvent

sealed class VerifyOtpUIEvent {

    data class OtpChange(val otp: String) : VerifyOtpUIEvent()
    object Submit : VerifyOtpUIEvent()

}