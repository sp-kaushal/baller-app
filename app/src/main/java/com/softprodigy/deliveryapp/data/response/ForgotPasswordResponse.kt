package com.example.deliveryprojectstructuredemo.data.response

import com.example.deliveryprojectstructuredemo.data.UserInfo
import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("data") var userInfo: UserInfo = UserInfo(),
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int,
    @SerializedName("verifyToken") val verifyToken: String
)