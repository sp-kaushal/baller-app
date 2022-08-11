package com.softprodigy.deliveryapp.ui.features.login

import com.softprodigy.deliveryapp.common.AppConstants
import com.softprodigy.deliveryapp.common.ResultWrapper
import com.softprodigy.deliveryapp.data.UserStorage
import com.softprodigy.deliveryapp.data.request.LoginRequest
import com.softprodigy.deliveryapp.data.response.GoogleLoginResponse
import com.softprodigy.deliveryapp.data.response.LoginResponse
import com.softprodigy.deliveryapp.network.APIService
import com.softprodigy.deliveryapp.common.safeApiCall
import com.softprodigy.deliveryapp.data.datastore.DataStoreManager
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