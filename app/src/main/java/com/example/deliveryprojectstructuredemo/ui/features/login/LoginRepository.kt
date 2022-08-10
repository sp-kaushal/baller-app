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
import com.example.deliveryprojectstructuredemo.data.datastore.DataStoreManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface LoginRepository {
    suspend fun loginWithEmailAndPass(email: String, password: String): RepositoryResult<LoginResponse>
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
    ): RepositoryResult<LoginResponse> {

     /*  ` val requestBody =
            LoginRequest(
                email = email,
                password = password,
                deviceType = AppConstants.ANDROID,
                deviceToken = UserStorage.deviceHesh
            )
        return safeApiCall(dispatcher) { service.userLogin(requestBody) }*/
        val requestBody =  LoginRequest(
            email = email,
            password = password,
            deviceType = AppConstants.ANDROID,
            deviceToken = UserStorage.deviceHesh
        )
        when(val response= getAnswerSuccess(service.userLogin(requestBody))){
            is RepositoryResult.Success -> {
                response.data?.userInfo?.let { userInfo ->
                    UserStorage.token = userInfo.token
                    val dataObject = JsonObject()
                    dataObject.addProperty(ApiConstants.first_name, userInfo.firstName)
                    dataObject.addProperty(ApiConstants.last_name, userInfo.lastName)
                    dataObject.addProperty(ApiConstants.mobile, userInfo.mobile)
                    dataObject.addProperty(ApiConstants.email, userInfo.email)
                    dataObject.addProperty(ApiConstants.token, userInfo.token)
                    dataStoreManager.setUserInfo(dataObject.toString())
                    return RepositoryResult.Success(response.data)
                } ?: run {
                    return RepositoryResult.Error("Empty Body")
                }
            }
            is RepositoryResult.Error -> {
                return response
            }

        }
    }

    override suspend fun loginWithGoogle(googleUser: String): ResultWrapper<GoogleLoginResponse> {
        TODO("Not yet implemented")
    }
}