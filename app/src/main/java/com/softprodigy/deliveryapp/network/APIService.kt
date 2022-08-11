package com.softprodigy.deliveryapp.network

import com.softprodigy.deliveryapp.common.ApiConstants
import com.softprodigy.deliveryapp.data.request.LoginRequest
import com.softprodigy.deliveryapp.data.request.SignUpRequest
import com.softprodigy.deliveryapp.data.response.LoginResponse
import com.softprodigy.deliveryapp.data.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {

    @POST(ApiConstants.LOGIN)
    suspend fun userLogin(@Body loginRequest: LoginRequest):LoginResponse

    @POST(ApiConstants.SIGNUP)
    suspend fun userSignUp(@Body signUpRequest: SignUpRequest):SignUpResponse
}