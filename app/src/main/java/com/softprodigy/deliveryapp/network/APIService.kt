package com.softprodigy.deliveryapp.network

import com.softprodigy.deliveryapp.data.response.ForgotPasswordResponse
import com.softprodigy.deliveryapp.data.response.ResetPasswordResponse
import com.softprodigy.deliveryapp.data.response.VerifyOtpResponse
import com.softprodigy.deliveryapp.common.ApiConstants
import com.softprodigy.deliveryapp.data.request.*
import com.softprodigy.deliveryapp.data.response.LoginResponse
import com.softprodigy.deliveryapp.data.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {

    @POST(ApiConstants.LOGIN)
    suspend fun userLogin(@Body loginRequest: LoginRequest):LoginResponse

    @POST(ApiConstants.SOCIAL_LOGIN)
    suspend fun socialLogin(@Body loginRequest: SocialLoginRequest):LoginResponse

    @POST(ApiConstants.SIGNUP)
    suspend fun userSignUp(@Body signUpRequest: SignUpRequest):SignUpResponse

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