package com.example.deliveryprojectstructuredemo.data.response

import com.example.deliveryprojectstructuredemo.data.UserInfo
import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(
    @SerializedName("data")
    val userInfo: UserInfo = UserInfo(),
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)