package com.softprodigy.deliveryapp.ui.features.otp_verification


sealed class VerifyOtpUIEvent {

    data class OtpChange(val otp: String) : VerifyOtpUIEvent()
    object Submit : VerifyOtpUIEvent()

}