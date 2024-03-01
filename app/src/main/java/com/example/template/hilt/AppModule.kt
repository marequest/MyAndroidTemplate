package com.example.template.hilt

import android.content.Context
import com.example.network.network.RetrofitClient
import com.example.network.network.helpers.AuthServiceHelper
import com.example.network.network.repositories.EncryptedUserSessionRepository
import com.example.network.network.repositories.UserSessionRepository
import com.example.network.network.services.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserSessionRepository(@ApplicationContext context: Context): UserSessionRepository {
        return EncryptedUserSessionRepository(context)
    }
    @Provides
    @Singleton
    fun provideAuthServiceHelper(): AuthServiceHelper {
        return AuthServiceHelper()
    }
    @Singleton
    @Provides
    fun provideAuthService(): AuthService {
        return RetrofitClient.getRetrofitInstance().create(AuthService::class.java)
    }
}
