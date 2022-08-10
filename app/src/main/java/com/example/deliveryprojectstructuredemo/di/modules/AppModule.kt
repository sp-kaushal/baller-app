package com.example.deliveryprojectstructuredemo.di.modules

import android.content.Context
import com.example.deliveryprojectstructuredemo.BuildConfig
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
    /**
     * Application's Base URL
     */
    @Provides
    fun providesAppBaseURL() = ApiConstants.BASE_URL


    /**
     * Logger For API
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context/*, sessionDataManager: SessionDataManager*/): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
//        okHttpClient.addInterceptor(AuthInterceptor(sessionDataManager))
//        okHttpClient.addInterceptor(LoggingInterceptor(context))
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(loggingInterceptor)
        }
        return okHttpClient.build()
    }


    /**
     * Init Retrofit Client for Network
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): APIService =
        retrofit.create(APIService::class.java)


    @Provides
    @Singleton
    fun provideLoginRepo(apiService: APIService): LoginRepository =
        RepositoryImpl(apiService)


    @Provides
    @Singleton
    fun provideSignUpRepo(apiService: APIService): SignUpRepository =
        SignUpRepoImpl(apiService)

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