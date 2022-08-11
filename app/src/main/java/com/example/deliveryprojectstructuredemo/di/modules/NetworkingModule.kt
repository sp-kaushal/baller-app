package com.example.deliveryprojectstructuredemo.di.modules

import com.example.deliveryprojectstructuredemo.BuildConfig.*
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
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.slack.eithernet.ApiResultCallAdapterFactory
import com.slack.eithernet.ApiResultConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClient.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClient.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClient.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        okHttpClient.followSslRedirects(true)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRestApiService(okHttpClient: OkHttpClient): APIService {
        return Retrofit.Builder()
            .baseUrl(API_SERVER)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .addConverterFactory(ApiResultConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

}