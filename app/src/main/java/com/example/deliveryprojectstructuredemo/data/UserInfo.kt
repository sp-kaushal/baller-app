package com.example.deliveryprojectstructuredemo.data

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("email") var email: String = "",
    @SerializedName("token") var token: String? = null
)