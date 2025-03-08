package com.tedmoon99.trave_journal_for_everyone.di

import android.content.Context
import com.tedmoon99.data.kakao.datasource.KakaoService
import com.tedmoon99.data.kakao.repository.KakaoRepositoryImpl
import com.tedmoon99.domain.kakao.repository.KakaoRepository
import com.tedmoon99.domain.kakao.usecase.KakaoUseCase
import com.tedmoon99.domain.kakao.usecase.KakaoUseCaseImpl
import com.tedmoon99.domain.member.repository.MemberRepository
import com.tedmoon99.domain.token.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoModule {

    @Provides
    @Singleton
    fun provideKakaoService(@NetworkModule.BaseClient retrofit: Retrofit): KakaoService = retrofit.create()

    @Provides
    @Singleton
    fun provideKakaoRepository(
        @ApplicationContext context: Context,
        kakoService: KakaoService,
        tokenRepository: TokenRepository,
        memberRepository: MemberRepository,
    ): KakaoRepository {
        return KakaoRepositoryImpl(context, kakoService, tokenRepository, memberRepository)
    }

    @Provides
    @Singleton
    fun provideKakaoUseCase(
        kakaoRepository: KakaoRepository
    ): KakaoUseCase {
        return KakaoUseCaseImpl(kakaoRepository)
    }
}