package com.example.deliveryprojectstructuredemo.ui.features.create_new_password

import com.example.deliveryprojectstructuredemo.common.ResultWrapper
import com.example.deliveryprojectstructuredemo.common.safeApiCall
import com.example.deliveryprojectstructuredemo.data.request.ForgotPasswordRequest
import com.example.deliveryprojectstructuredemo.data.request.ResetPasswordRequest
import com.example.deliveryprojectstructuredemo.data.response.ForgotPasswordResponse
import com.example.deliveryprojectstructuredemo.data.response.ResetPasswordResponse
import com.example.deliveryprojectstructuredemo.network.APIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface ResetPasswordRepository {
    suspend fun resetPassword(token: String, password: String): ResultWrapper<ResetPasswordResponse>
}

class ResetPasswordRepoImpl @Inject constructor(
    private val service: APIService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ResetPasswordRepository {
    override suspend fun resetPassword(
        token: String,
        password: String
    ): ResultWrapper<ResetPasswordResponse> {
        val requestBody = ResetPasswordRequest(password = password)
        return safeApiCall(dispatcher) { service.resetPassword(token, requestBody) }
    }

}