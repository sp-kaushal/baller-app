package com.example.deliveryprojectstructuredemo.data.response

import com.example.deliveryprojectstructuredemo.data.UserInfo
import com.google.gson.annotations.SerializedName


data class SignUpResponse(
    @SerializedName("data") var userInfo: UserInfo = UserInfo(),
    @SerializedName("status") var status: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("verifyToken") var verifyToken: String? = null,

)


