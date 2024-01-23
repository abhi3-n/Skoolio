package com.project.skoolio.di

import com.project.skoolio.network.Backend
import com.project.skoolio.repositories.BackendRepository
import com.project.skoolio.repositories.RegistrationScreenRepository
import com.project.skoolio.utils.Constants
import com.project.skoolio.viewModels.LoginViewModel
import com.project.skoolio.viewModels.RegistrationScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideBackend(): Backend {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Backend::class.java)
    }

    @Provides
    @Singleton
    fun provideBackendRepository(backend: Backend) = BackendRepository(backend)
    @Provides
    @Singleton
    fun provideRegistrationScreenRepository(backend: Backend) = RegistrationScreenRepository(backend)


    @Provides
    @Singleton
    fun provideLoginViewModel(backendRepository: BackendRepository) = LoginViewModel(backendRepository)
    @Provides
    @Singleton
    fun provideRegistrationScreenViewModel(registrationScreenRepository: RegistrationScreenRepository) = RegistrationScreenViewModel(registrationScreenRepository)

}