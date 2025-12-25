package com.matrix.multigpt.di

import android.content.Context
import com.matrix.multigpt.util.SecureCredentialManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Provides
    @Singleton
    fun provideSecureCredentialManager(
        @ApplicationContext context: Context
    ): SecureCredentialManager = SecureCredentialManager(context)
}
