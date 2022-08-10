package com.example.deliveryprojectstructuredemo.data.request

import com.google.gson.annotations.SerializedName

class VerifyOtpRequest(
    @SerializedName("otp") val otp: String,
    @SerializedName("otoType") val otpType: String
)