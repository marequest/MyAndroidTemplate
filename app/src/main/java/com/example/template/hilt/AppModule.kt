package com.example.template.hilt

import android.content.Context
import com.example.network.network.repositories.EncryptedUserSessionRepository
import com.example.network.network.repositories.UserSessionRepository
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
}
