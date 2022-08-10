package com.example.deliveryprojectstructuredemo.data.response

import com.example.deliveryprojectstructuredemo.data.UserInfo
import com.google.gson.annotations.SerializedName

class ResetPasswordResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val userInfo: UserInfo = UserInfo()
)