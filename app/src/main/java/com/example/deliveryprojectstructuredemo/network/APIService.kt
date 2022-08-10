package com.example.deliveryprojectstructuredemo.network

import com.example.deliveryprojectstructuredemo.common.ApiConstants
import com.example.deliveryprojectstructuredemo.data.request.ForgotPasswordRequest
import com.example.deliveryprojectstructuredemo.data.request.LoginRequest
import com.example.deliveryprojectstructuredemo.data.request.SignUpRequest
import com.example.deliveryprojectstructuredemo.data.request.VerifyOtpRequest
import com.example.deliveryprojectstructuredemo.data.response.ForgotPasswordResponse
import com.example.deliveryprojectstructuredemo.data.response.LoginResponse
import com.example.deliveryprojectstructuredemo.data.response.SignUpResponse
import com.example.deliveryprojectstructuredemo.data.response.VerifyOtpResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {

    @POST(ApiConstants.LOGIN)
    suspend fun userLogin(@Body loginRequest: LoginRequest): LoginResponse

    @POST(ApiConstants.SIGNUP)
    suspend fun userSignUp(@Body signUpRequest: SignUpRequest): SignUpResponse

    @POST(ApiConstants.FORGOT_PASSWORD)
    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse

    @POST(ApiConstants.VERIFY_OTP)
    suspend fun verifyOtp(
        @Header("Authorization") token: String,
        @Body verifyOtpRequest: VerifyOtpRequest
    ): VerifyOtpResponse
}