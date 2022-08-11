package com.example.deliveryprojectstructuredemo.ui.features.login

import com.example.deliveryprojectstructuredemo.common.AppConstants
import com.example.deliveryprojectstructuredemo.common.ResultWrapper
import com.example.deliveryprojectstructuredemo.data.UserStorage
import com.example.deliveryprojectstructuredemo.data.request.LoginRequest
import com.example.deliveryprojectstructuredemo.data.response.GoogleLoginResponse
import com.example.deliveryprojectstructuredemo.data.response.LoginResponse
import com.example.deliveryprojectstructuredemo.network.APIService
import com.google.gson.JsonObject
import com.delivery_app.core.model.RepositoryResult
import com.delivery_app.core.util.getAnswerSuccess
import com.example.deliveryprojectstructuredemo.common.ApiConstants
import com.example.deliveryprojectstructuredemo.common.safeApiCall
import com.example.deliveryprojectstructuredemo.data.datastore.DataStoreManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface LoginRepository {
    suspend fun loginWithEmailAndPass(email: String, password: String): ResultWrapper<LoginResponse>
    suspend fun loginWithGoogle(googleUser: String): ResultWrapper<GoogleLoginResponse>
}

@Singleton
class RepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val service: APIService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LoginRepository {
    override suspend fun loginWithEmailAndPass(
        email: String,
        password: String,
    ): ResultWrapper<LoginResponse> {
       val requestBody =
            LoginRequest(
                email = email,
                password = password,
                deviceType = AppConstants.ANDROID,
                deviceToken = UserStorage.deviceHesh
            )
        return safeApiCall(dispatcher) { service.userLogin(requestBody) }
    }

    override suspend fun loginWithGoogle(googleUser: String): ResultWrapper<GoogleLoginResponse> {
        TODO("Not yet implemented")
    }
}