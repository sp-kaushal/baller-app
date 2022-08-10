package com.example.deliveryprojectstructuredemo.data.request

import com.google.gson.annotations.SerializedName

class ResetPasswordRequest(@SerializedName("password") var password: String? = null)