package com.example.deliveryprojectstructuredemo.di.modules

import android.content.Context
import com.example.deliveryprojectstructuredemo.BuildConfig
import com.example.deliveryprojectstructuredemo.DeliveryApp
import com.example.deliveryprojectstructuredemo.common.ApiConstants
import com.example.deliveryprojectstructuredemo.network.APIService
import com.example.deliveryprojectstructuredemo.ui.features.login.LoginRepository
import com.example.deliveryprojectstructuredemo.ui.features.login.RepositoryImpl
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

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): DeliveryApp {
        return app as DeliveryApp
    }
}