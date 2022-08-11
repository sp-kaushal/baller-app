package com.softprodigy.deliveryapp.data

import com.google.gson.annotations.SerializedName




data class ErrorResponse(
    @SerializedName("Code") var Code: Int? = null,
    @SerializedName("Error") var Error: String = ""
)

