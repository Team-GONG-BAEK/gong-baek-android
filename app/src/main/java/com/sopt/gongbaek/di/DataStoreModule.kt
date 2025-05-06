package com.sopt.gongbaek.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import com.sopt.gongbaek.data.security.AuthTokens
import com.sopt.gongbaek.data.security.AuthTokensSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideAuthTokensDataStore(
        @ApplicationContext context: Context
    ): DataStore<AuthTokens> = DataStoreFactory.create(
        serializer = AuthTokensSerializer,
        produceFile = { File(context.filesDir, "auth_tokens.pb") },
        corruptionHandler = ReplaceFileCorruptionHandler { AuthTokens("", "", "") }
    )
}
