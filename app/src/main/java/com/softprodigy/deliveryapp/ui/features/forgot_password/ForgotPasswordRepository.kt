package com.softprodigy.deliveryapp.ui.features.forgot_password


import com.softprodigy.deliveryapp.data.request.ForgotPasswordRequest
import com.softprodigy.deliveryapp.common.ResultWrapper
import com.softprodigy.deliveryapp.common.safeApiCall
import com.softprodigy.deliveryapp.data.response.ForgotPasswordResponse
import com.softprodigy.deliveryapp.network.APIService
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