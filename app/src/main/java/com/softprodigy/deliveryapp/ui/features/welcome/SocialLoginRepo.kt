package com.softprodigy.deliveryapp.ui.features.welcome

import com.softprodigy.deliveryapp.common.AppConstants
import com.softprodigy.deliveryapp.common.ResultWrapper
import com.softprodigy.deliveryapp.common.safeApiCall
import com.softprodigy.deliveryapp.data.GoogleUserModel
import com.softprodigy.deliveryapp.data.UserStorage
import com.softprodigy.deliveryapp.data.datastore.DataStoreManager
import com.softprodigy.deliveryapp.data.request.SocialLoginRequest
import com.softprodigy.deliveryapp.data.response.LoginResponse
import com.softprodigy.deliveryapp.network.APIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface SocialLoginRepo {
    suspend fun loginWithGoogle(googleUser: GoogleUserModel): ResultWrapper<LoginResponse>
}

@Singleton
class SocialLoginRepoImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val service: APIService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
):SocialLoginRepo {
    override suspend fun loginWithGoogle(googleUser: GoogleUserModel): ResultWrapper<LoginResponse> {
        val requestBody =
            SocialLoginRequest(
                firstName = googleUser.name,
                lastName = googleUser.name,
                providerId = googleUser.id,
                providerToken = googleUser.token,
                provider_type = AppConstants.GOOGLE,
                email = googleUser.email,
                deviceType = AppConstants.ANDROID,
                deviceToken = UserStorage.deviceHesh,
            )
        return safeApiCall(dispatcher) { service.socialLogin(requestBody) }
    }

}