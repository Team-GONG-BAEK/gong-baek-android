package com.gongbaek.android.di

import com.gongbaek.android.data.remote.service.CommentService
import com.gongbaek.android.data.remote.service.AuthService
import com.gongbaek.android.data.remote.service.SearchService
import com.gongbaek.android.data.remote.service.GroupService
import com.gongbaek.android.data.remote.service.TokenReissueService
import com.gongbaek.android.data.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Provides
    @Singleton
    fun provideGroupService(retrofit: Retrofit): GroupService =
        retrofit.create(GroupService::class.java)

    @Provides
    @Singleton
    fun provideCommentService(retrofit: Retrofit): CommentService =
        retrofit.create(CommentService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideTokenReissueService(retrofit: Retrofit): TokenReissueService =
        retrofit.create(TokenReissueService::class.java)
}
