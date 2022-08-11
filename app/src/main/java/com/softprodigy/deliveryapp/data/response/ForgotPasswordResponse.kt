package com.softprodigy.deliveryapp.data.response

import com.google.gson.annotations.SerializedName
import com.softprodigy.deliveryapp.data.UserInfo

data class ForgotPasswordResponse(
    @SerializedName("data") var userInfo: UserInfo = UserInfo(),
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int,
    @SerializedName("verifyToken") val verifyToken: String
)