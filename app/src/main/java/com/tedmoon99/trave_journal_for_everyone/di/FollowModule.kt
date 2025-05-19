package com.tedmoon99.trave_journal_for_everyone.di

import com.tedmoon99.data.follow.datasource.FollowService
import com.tedmoon99.data.follow.repository.FollowRepositoryImpl
import com.tedmoon99.domain.follow.repository.FollowRepository
import com.tedmoon99.domain.follow.usecase.FollowUseCase
import com.tedmoon99.domain.follow.usecase.FollowUseCaseImpl
import com.tedmoon99.domain.member.repository.MemberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FollowModule {

    @Provides
    @Singleton
    fun providesFollowService(@NetworkModule.BaseClient retrofit: Retrofit): FollowService = retrofit.create()

    @Provides
    @Singleton
    fun provideFollowRepository(
        followService: FollowService,
        memberRepository: MemberRepository,
    ): FollowRepository {
        return FollowRepositoryImpl(followService, memberRepository)
    }

    @Provides
    @Singleton
    fun provideFollowUseCase(
        followRepository: FollowRepository
    ): FollowUseCase {
        return FollowUseCaseImpl(followRepository)
    }
}