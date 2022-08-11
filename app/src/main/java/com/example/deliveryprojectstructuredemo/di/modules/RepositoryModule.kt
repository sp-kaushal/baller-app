package com.example.deliveryprojectstructuredemo.di.modules

import com.example.deliveryprojectstructuredemo.data.datastore.DataStoreManager
import com.example.deliveryprojectstructuredemo.network.APIService
import com.example.deliveryprojectstructuredemo.ui.features.create_new_password.ResetPasswordRepoImpl
import com.example.deliveryprojectstructuredemo.ui.features.create_new_password.ResetPasswordRepository
import com.example.deliveryprojectstructuredemo.ui.features.forgot_password.ForgotPasswordRepoImpl
import com.example.deliveryprojectstructuredemo.ui.features.forgot_password.ForgotPasswordRepository
import com.example.deliveryprojectstructuredemo.ui.features.login.LoginRepository
import com.example.deliveryprojectstructuredemo.ui.features.login.RepositoryImpl
import com.example.deliveryprojectstructuredemo.ui.features.otp_verification.VerifyOtpRepoImpl
import com.example.deliveryprojectstructuredemo.ui.features.otp_verification.VerifyOtpRepository
import com.example.deliveryprojectstructuredemo.ui.features.sign_up.SignUpRepoImpl
import com.example.deliveryprojectstructuredemo.ui.features.sign_up.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepo(
        apiService: APIService,
        dataStoreManager: DataStoreManager,
    ): LoginRepository {
        return RepositoryImpl(service = apiService, dataStoreManager = dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideSignUpRepo(
        apiService: APIService,
        dataStoreManager: DataStoreManager,
    ): SignUpRepository {
        return SignUpRepoImpl(service = apiService, dataStoreManager = dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideForgotPassRepo(apiService: APIService): ForgotPasswordRepository =
        ForgotPasswordRepoImpl(apiService)

    @Provides
    @Singleton
    fun provideVerifyOtp(apiService: APIService): VerifyOtpRepository =
        VerifyOtpRepoImpl(apiService)

    @Provides
    @Singleton
    fun provideResetPassword(apiService: APIService): ResetPasswordRepository =
        ResetPasswordRepoImpl(apiService)

}
