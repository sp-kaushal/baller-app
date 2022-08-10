package com.example.deliveryprojectstructuredemo.ui.features.otp_verification

import com.example.deliveryprojectstructuredemo.common.ResultWrapper
import com.example.deliveryprojectstructuredemo.common.safeApiCall
import com.example.deliveryprojectstructuredemo.data.request.ForgotPasswordRequest
import com.example.deliveryprojectstructuredemo.data.request.VerifyOtpRequest
import com.example.deliveryprojectstructuredemo.data.response.ForgotPasswordResponse
import com.example.deliveryprojectstructuredemo.data.response.VerifyOtpResponse
import com.example.deliveryprojectstructuredemo.network.APIService
import com.example.deliveryprojectstructuredemo.ui.features.forgot_password.ForgotPasswordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface VerifyOtpRepository {

    suspend fun verifyOtp(
        token: String,
        otpType: String,
        otp: String
    ): ResultWrapper<VerifyOtpResponse>

}

class VerifyOtpRepoImpl @Inject constructor(
    private val service: APIService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : VerifyOtpRepository {

    override suspend fun verifyOtp(
        token: String,
        otpType: String,
        otp: String
    ): ResultWrapper<VerifyOtpResponse> {
        val requestBody = VerifyOtpRequest(otp, otpType)
        return safeApiCall(dispatcher) { service.verifyOtp(token, requestBody) }
    }
}