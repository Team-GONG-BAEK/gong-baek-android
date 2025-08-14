package com.gongbaek.android.di

import com.gongbaek.android.domain.repository.AuthRepository
import com.gongbaek.android.domain.repository.CommentRepository
import com.gongbaek.android.domain.repository.GroupRepository
import com.gongbaek.android.domain.repository.LectureTimetableRepository
import com.gongbaek.android.domain.repository.SearchRepository
import com.gongbaek.android.domain.repository.TokenRepository
import com.gongbaek.android.domain.repository.UserRepository
import com.gongbaek.android.domain.usecase.ApplyGroupUseCase
import com.gongbaek.android.domain.usecase.CancelGroupUseCase
import com.gongbaek.android.domain.usecase.DeleteCommentUseCase
import com.gongbaek.android.domain.usecase.DeleteGroupUseCase
import com.gongbaek.android.domain.usecase.FetchHomeScreenUseCase
import com.gongbaek.android.domain.usecase.FetchLatestGroupUseCase
import com.gongbaek.android.domain.usecase.FetchUserLectureTimetableUseCase
import com.gongbaek.android.domain.usecase.FetchUserProfileUseCase
import com.gongbaek.android.domain.usecase.GetGroupCommentsUseCase
import com.gongbaek.android.domain.usecase.GetGroupsUseCase
import com.gongbaek.android.domain.usecase.GetLectureTimetableUseCase
import com.gongbaek.android.domain.usecase.GetMyGroupsUseCase
import com.gongbaek.android.domain.usecase.GetMyProfileUseCase
import com.gongbaek.android.domain.usecase.LoadGroupDetailScreenUseCase
import com.gongbaek.android.domain.usecase.LoadGroupRoomScreenUseCase
import com.gongbaek.android.domain.usecase.LoginUseCase
import com.gongbaek.android.domain.usecase.LogoutUseCase
import com.gongbaek.android.domain.usecase.PostCommentUseCase
import com.gongbaek.android.domain.usecase.PostGroupUseCase
import com.gongbaek.android.domain.usecase.ReissueTokenUseCase
import com.gongbaek.android.domain.usecase.ReportCommentUseCase
import com.gongbaek.android.domain.usecase.ReportGroupUseCase
import com.gongbaek.android.domain.usecase.RequestEmailVerificationUseCase
import com.gongbaek.android.domain.usecase.RequestSignUpUseCase
import com.gongbaek.android.domain.usecase.SearchMajorsUseCase
import com.gongbaek.android.domain.usecase.SearchUniversitiesUseCase
import com.gongbaek.android.domain.usecase.SetLectureTimetableUseCase
import com.gongbaek.android.domain.usecase.SetTokenUseCase
import com.gongbaek.android.domain.usecase.ValidateNicknameUseCase
import com.gongbaek.android.domain.usecase.VerifyEmailCodeUseCase
import com.gongbaek.android.domain.usecase.WithdrawUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetSearchUniversitiesResultUseCase(
        searchRepository: SearchRepository
    ): SearchUniversitiesUseCase = SearchUniversitiesUseCase(searchRepository)

    @Provides
    @Singleton
    fun provideGetSearchMajorsResultUseCase(
        searchRepository: SearchRepository
    ): SearchMajorsUseCase = SearchMajorsUseCase(searchRepository)

    @Provides
    @Singleton
    fun provideGetMyGroupsUseCase(
        groupRepository: GroupRepository
    ): GetMyGroupsUseCase = GetMyGroupsUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideLoadGroupDetailScreenUseCase(
        groupRepository: GroupRepository,
        commentRepository: CommentRepository
    ): LoadGroupDetailScreenUseCase = LoadGroupDetailScreenUseCase(groupRepository, commentRepository)

    @Provides
    @Singleton
    fun provideApplyGroupUseCase(
        groupRepository: GroupRepository
    ): ApplyGroupUseCase = ApplyGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideGetGroupCommentsUseCase(
        commentRepository: CommentRepository
    ): GetGroupCommentsUseCase = GetGroupCommentsUseCase(commentRepository)

    @Provides
    @Singleton
    fun providesPostCommentUseCase(
        commentRepository: CommentRepository
    ): PostCommentUseCase = PostCommentUseCase(commentRepository)

    @Provides
    @Singleton
    fun provideRegisterUserInfoUseCase(
        authRepository: AuthRepository
    ): RequestSignUpUseCase = RequestSignUpUseCase(authRepository)

    @Provides
    @Singleton
    fun provideSetTokenUseCase(
        tokenRepository: TokenRepository
    ): SetTokenUseCase = SetTokenUseCase(tokenRepository)

    @Provides
    @Singleton
    fun provideValidateNicknameUseCase(
        authRepository: AuthRepository
    ): ValidateNicknameUseCase = ValidateNicknameUseCase(authRepository)

    @Provides
    @Singleton
    fun provideGetGroupsUseCase(
        groupRepository: GroupRepository
    ): GetGroupsUseCase = GetGroupsUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideFetchHomeScreenUseCase(
        groupRepository: GroupRepository
    ): FetchHomeScreenUseCase = FetchHomeScreenUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideFetchLatestGroupUseCase(
        groupRepository: GroupRepository
    ): FetchLatestGroupUseCase = FetchLatestGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideFetchUserProfileUseCase(
        authRepository: AuthRepository
    ): FetchUserProfileUseCase = FetchUserProfileUseCase(authRepository)

    @Provides
    @Singleton
    fun provideFetchUserLectureTimetableUseCase(
        authRepository: AuthRepository
    ): FetchUserLectureTimetableUseCase = FetchUserLectureTimetableUseCase(authRepository)

    @Provides
    @Singleton
    fun provideGetLectureTimetableUseCase(
        lectureTimetableRepository: LectureTimetableRepository
    ): GetLectureTimetableUseCase = GetLectureTimetableUseCase(lectureTimetableRepository)

    @Provides
    @Singleton
    fun provideSetLectureTimetableUseCase(
        lectureTimetableRepository: LectureTimetableRepository
    ): SetLectureTimetableUseCase = SetLectureTimetableUseCase(lectureTimetableRepository)

    @Provides
    @Singleton
    fun providePostGroupUseCase(
        groupRepository: GroupRepository
    ): PostGroupUseCase = PostGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideLoadGroupRoomScreenUseCase(
        groupRepository: GroupRepository,
        commentRepository: CommentRepository
    ): LoadGroupRoomScreenUseCase = LoadGroupRoomScreenUseCase(groupRepository, commentRepository)

    @Provides
    @Singleton
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ): LoginUseCase = LoginUseCase(authRepository)

    @Provides
    @Singleton
    fun provideReissueTokenUseCase(
        tokenRepository: TokenRepository
    ): ReissueTokenUseCase = ReissueTokenUseCase(tokenRepository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        authRepository: AuthRepository
    ): LogoutUseCase = LogoutUseCase(authRepository)

    @Provides
    @Singleton
    fun provideWithdrawUseCase(
        authRepository: AuthRepository
    ): WithdrawUseCase = WithdrawUseCase(authRepository)

    @Provides
    @Singleton
    fun provideGetMyProfileUseCase(
        userRepository: UserRepository
    ): GetMyProfileUseCase = GetMyProfileUseCase(userRepository)

    @Provides
    @Singleton
    fun provideRequestEmailVerificationUseCase(
        authRepository: AuthRepository
    ): RequestEmailVerificationUseCase = RequestEmailVerificationUseCase(authRepository)

    @Provides
    @Singleton
    fun provideVerifyEmailCodeUseCase(
        authRepository: AuthRepository
    ): VerifyEmailCodeUseCase = VerifyEmailCodeUseCase(authRepository)

    @Provides
    @Singleton
    fun provideDeleteCommentUseCase(
        commentRepository: CommentRepository
    ): DeleteCommentUseCase = DeleteCommentUseCase(commentRepository)

    @Provides
    @Singleton
    fun provideDeleteGroupUseCase(
        groupRepository: GroupRepository
    ): DeleteGroupUseCase = DeleteGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideCancelGroupUseCase(
        groupRepository: GroupRepository
    ): CancelGroupUseCase = CancelGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideReportGroupUseCase(
        groupRepository: GroupRepository
    ): ReportGroupUseCase = ReportGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun provideReportCommentUseCase(
        commentRepository: CommentRepository
    ): ReportCommentUseCase = ReportCommentUseCase(commentRepository)
}
