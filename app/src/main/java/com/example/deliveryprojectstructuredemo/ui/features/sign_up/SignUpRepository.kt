package com.example.deliveryprojectstructuredemo.ui.features.sign_up

import com.delivery_app.core.model.RepositoryResult
import com.delivery_app.core.util.getAnswerSuccess
import com.example.deliveryprojectstructuredemo.common.ApiConstants
import com.example.deliveryprojectstructuredemo.common.AppConstants
import com.example.deliveryprojectstructuredemo.common.ResultWrapper
import com.example.deliveryprojectstructuredemo.data.UserStorage
import com.example.deliveryprojectstructuredemo.data.datastore.DataStoreManager
import com.example.deliveryprojectstructuredemo.data.request.SignUpRequest
import com.example.deliveryprojectstructuredemo.data.response.SignUpResponse
import com.example.deliveryprojectstructuredemo.network.APIService
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface SignUpRepository {
    suspend fun signUpWithDetails(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): RepositoryResult<SignUpResponse>

    suspend fun SignUpWithGoogle(googleUser: String): ResultWrapper<SignUpResponse>
}

@Singleton
class SignUpRepoImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val service: APIService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SignUpRepository {
    override suspend fun signUpWithDetails(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
    ): RepositoryResult<SignUpResponse> {
        val requestBody =
            SignUpRequest(
                firstName = firstName,
                lastName = lastName,
                mobile = "1111111111",
                email = email,
                password = password,
                deviceType = AppConstants.ANDROID,
                deviceToken = UserStorage.deviceHesh
            )
        when (val response = getAnswerSuccess(service.userSignUp(requestBody))) {
            is RepositoryResult.Success -> {
                response.data?.verifyToken?.let { verifyToken ->
                    UserStorage.verifyToken = verifyToken
                }

                response.data?.userInfo?.let { userInfo ->
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

    override suspend fun SignUpWithGoogle(googleUser: String): ResultWrapper<SignUpResponse> {
        TODO("Not yet implemented")
    }
}