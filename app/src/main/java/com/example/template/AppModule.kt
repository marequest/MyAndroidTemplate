package com.example.template

import android.content.Context
import com.example.network.network.EncryptedUserSessionRepository
import com.example.network.network.UserSessionRepository
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
