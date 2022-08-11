package com.example.deliveryprojectstructuredemo.di.modules

import android.content.Context
import com.example.deliveryprojectstructuredemo.BuildConfig
import com.example.deliveryprojectstructuredemo.DeliveryApp
import com.example.deliveryprojectstructuredemo.common.ApiConstants
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    fun provideApplication(@ApplicationContext app: Context): DeliveryApp {
        return app as DeliveryApp
    }
}