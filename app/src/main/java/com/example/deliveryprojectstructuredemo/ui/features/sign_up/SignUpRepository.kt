package com.example.deliveryprojectstructuredemo.ui.features.sign_up

import com.example.deliveryprojectstructuredemo.common.AppConstants
import com.example.deliveryprojectstructuredemo.common.ResultWrapper
import com.example.deliveryprojectstructuredemo.common.safeApiCall
import com.example.deliveryprojectstructuredemo.data.UserStorage
import com.example.deliveryprojectstructuredemo.data.request.LoginRequest
import com.example.deliveryprojectstructuredemo.data.request.SignUpRequest
import com.example.deliveryprojectstructuredemo.data.response.GoogleLoginResponse
import com.example.deliveryprojectstructuredemo.data.response.LoginResponse
import com.example.deliveryprojectstructuredemo.data.response.SignUpResponse
import com.example.deliveryprojectstructuredemo.network.APIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface SignUpRepository {
    suspend fun signUpWithDetails(firstName: String,lastName:String,email: String, password: String): ResultWrapper<SignUpResponse>
    suspend fun SignUpWithGoogle(googleUser: String): ResultWrapper<SignUpResponse>
}

@Singleton
class SignUpRepoImpl @Inject constructor(
    private val service: APIService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SignUpRepository {
    override suspend fun signUpWithDetails(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
    ): ResultWrapper<SignUpResponse> {
        val requestBody =
            SignUpRequest(
                firstName = firstName,
                lastName = lastName,
                mobile= "1111111111",
                email = email,
                password = password,
                deviceType = AppConstants.ANDROID,
                deviceToken = UserStorage.deviceHesh
            )
        return safeApiCall(dispatcher) { service.userSignUp(requestBody) }
    }

    override suspend fun SignUpWithGoogle(googleUser: String): ResultWrapper<SignUpResponse> {
        TODO("Not yet implemented")
    }
}