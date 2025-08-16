package com.gongbaek.android.di

import com.gongbaek.android.data.local.datasource.LectureTimetableLocalDataSource
import com.gongbaek.android.data.local.datasource.TokenLocalDataSource
import com.gongbaek.android.data.local.datasourceimpl.LectureTimetableLocalDataSourceImpl
import com.gongbaek.android.data.local.datasourceimpl.TokenLocalDataSourceImpl
import com.gongbaek.android.data.remote.datasource.CommentRemoteDataSource
import com.gongbaek.android.data.remote.datasource.AuthRemoteDataSource
import com.gongbaek.android.data.remote.datasource.GroupRemoteDataSource
import com.gongbaek.android.data.remote.datasourceimpl.CommentRemoteDataSourceImpl
import com.gongbaek.android.data.remote.datasource.SearchRemoteDataSource
import com.gongbaek.android.data.remote.datasource.TokenReissueRemoteDataSource
import com.gongbaek.android.data.remote.datasource.UserRemoteDataSource
import com.gongbaek.android.data.remote.datasourceimpl.AuthRemoteDatasourceImpl
import com.gongbaek.android.data.remote.datasourceimpl.GroupRemoteDataSourceImpl
import com.gongbaek.android.data.remote.datasourceimpl.SearchRemoteDataSourceImpl
import com.gongbaek.android.data.remote.datasourceimpl.TokenReissueRemoteDataSourceImpl
import com.gongbaek.android.data.remote.datasourceimpl.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindTokenLocalDataSource(
        tokenLocalDataSourceImpl: TokenLocalDataSourceImpl
    ): TokenLocalDataSource

    @Binds
    @Singleton
    abstract fun bindTokenReissueRemoteDataSource(
        tokenReissueRemoteDataSourceImpl: TokenReissueRemoteDataSourceImpl
    ): TokenReissueRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindGroupRemoteDataSource(
        groupRemoteDataSourceImpl: GroupRemoteDataSourceImpl
    ): GroupRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindCommentRemoteDataSource(
        commentRemoteDataSourceImpl: CommentRemoteDataSourceImpl
    ): CommentRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindSearchRemoteDataSource(
        searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl
    ): SearchRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDatasourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLectureTimetableLocalDataSource(
        lectureTimetableDataSourceImpl: LectureTimetableLocalDataSourceImpl
    ): LectureTimetableLocalDataSource

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource
}
