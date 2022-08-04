package com.example.deliveryprojectstructuredemo.data

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("errors") var errors: ArrayList<Errors> = arrayListOf()
)

data class Errors(
    @SerializedName("value") var value: String? = null,
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("param") var param: String? = null,
    @SerializedName("location") var location: String? = null

)
