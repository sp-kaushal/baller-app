package com.example.deliveryprojectstructuredemo.di.modules

import androidx.datastore.preferences.preferencesDataStore
import com.example.deliveryprojectstructuredemo.data.datastore.DataStoreManager
import com.example.deliveryprojectstructuredemo.network.APIService
import com.example.deliveryprojectstructuredemo.ui.features.login.LoginRepository
import com.example.deliveryprojectstructuredemo.ui.features.login.RepositoryImpl
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
}
