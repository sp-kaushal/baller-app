package com.example.deliveryprojectstructuredemo.ui.features.forgot_password

import com.example.deliveryprojectstructuredemo.common.ResultWrapper
import com.example.deliveryprojectstructuredemo.common.safeApiCall
import com.example.deliveryprojectstructuredemo.data.request.ForgotPasswordRequest
import com.example.deliveryprojectstructuredemo.data.response.ForgotPasswordResponse
import com.example.deliveryprojectstructuredemo.network.APIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface ForgotPasswordRepository {
    suspend fun forgotPassword(email: String): ResultWrapper<ForgotPasswordResponse>
}

class ForgotPasswordRepoImpl @Inject constructor(
    private val service: APIService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ForgotPasswordRepository {

    override suspend fun forgotPassword(email: String): ResultWrapper<ForgotPasswordResponse> {
        val requestBody = ForgotPasswordRequest(email = email)
        return safeApiCall(dispatcher) { service.forgotPassword(requestBody) }
    }
}