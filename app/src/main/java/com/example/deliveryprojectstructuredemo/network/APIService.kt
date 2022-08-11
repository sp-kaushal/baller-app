package com.example.deliveryprojectstructuredemo.network

import com.example.deliveryprojectstructuredemo.common.ApiConstants
import com.example.deliveryprojectstructuredemo.data.request.ForgotPasswordRequest
import com.example.deliveryprojectstructuredemo.data.request.LoginRequest
import com.example.deliveryprojectstructuredemo.data.request.ResetPasswordRequest
import com.example.deliveryprojectstructuredemo.data.request.SignUpRequest
import com.example.deliveryprojectstructuredemo.data.request.VerifyOtpRequest
import com.example.deliveryprojectstructuredemo.data.response.ForgotPasswordResponse
import com.example.deliveryprojectstructuredemo.data.response.LoginResponse
import com.example.deliveryprojectstructuredemo.data.response.ResetPasswordResponse
import com.example.deliveryprojectstructuredemo.data.response.SignUpResponse
import com.example.deliveryprojectstructuredemo.data.response.VerifyOtpResponse
import com.delivery_app.core.model.NetworkError
import com.slack.eithernet.ApiResult
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @POST(ApiConstants.LOGIN)
    suspend fun userLogin(@Body loginRequest: LoginRequest):LoginResponse

    @POST(ApiConstants.SIGNUP)
    suspend fun userSignUp(@Body signUpRequest: SignUpRequest): SignUpResponse

    @POST(ApiConstants.FORGOT_PASSWORD)
    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): ForgotPasswordResponse

    @POST("${ApiConstants.VERIFY_OTP}{token}")
    suspend fun verifyOtp(
        @Path("token") token: String,
        @Body verifyOtpRequest: VerifyOtpRequest
    ): VerifyOtpResponse

    @POST("${ApiConstants.RESET_PASS}{token}")
    suspend fun resetPassword(
        @Path("token") token: String,
        @Body resetPasswordRequest: ResetPasswordRequest
    ): ResetPasswordResponse

}