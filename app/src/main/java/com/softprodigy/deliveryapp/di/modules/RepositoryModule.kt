package com.softprodigy.deliveryapp.di.modules

import com.softprodigy.deliveryapp.data.datastore.DataStoreManager
import com.softprodigy.deliveryapp.network.APIService
import com.softprodigy.deliveryapp.ui.features.login.LoginRepository
import com.softprodigy.deliveryapp.ui.features.login.RepositoryImpl
import com.softprodigy.deliveryapp.ui.features.sign_up.SignUpRepoImpl
import com.softprodigy.deliveryapp.ui.features.sign_up.SignUpRepository
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
