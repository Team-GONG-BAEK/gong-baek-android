package com.gongbaek.android.di

import com.gongbaek.android.data.repositoryimpl.CommentRepositoryImpl
import com.gongbaek.android.data.repositoryimpl.AuthRepositoryImpl
import com.gongbaek.android.data.repositoryimpl.SearchRepositoryImpl
import com.gongbaek.android.data.repositoryimpl.GroupRepositoryImpl
import com.gongbaek.android.data.repositoryimpl.LectureTimetableRepositoryImpl
import com.gongbaek.android.data.repositoryimpl.TokenRepositoryImpl
import com.gongbaek.android.data.repositoryimpl.UserRepositoryImpl
import com.gongbaek.android.domain.repository.CommentRepository
import com.gongbaek.android.domain.repository.AuthRepository
import com.gongbaek.android.domain.repository.SearchRepository
import com.gongbaek.android.domain.repository.GroupRepository
import com.gongbaek.android.domain.repository.LectureTimetableRepository
import com.gongbaek.android.domain.repository.TokenRepository
import com.gongbaek.android.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    @Singleton
    abstract fun bindGroupRepository(
        groupRepositoryImpl: GroupRepositoryImpl
    ): GroupRepository

    @Binds
    @Singleton
    abstract fun bindCommentRepository(
        commentRepositoryImpl: CommentRepositoryImpl
    ): CommentRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindLectureTimetableRepository(
        lectureTimetableRepositoryImpl: LectureTimetableRepositoryImpl
    ): LectureTimetableRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}
