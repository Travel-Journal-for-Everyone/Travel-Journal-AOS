package com.tedmoon99.trave_journal_for_everyone.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tedmoon99.data.member.datasource.SignUpService
import com.tedmoon99.data.member.repository.MemberRepositoryImpl
import com.tedmoon99.data.member.repository.SignUpRepositoryImpl
import com.tedmoon99.domain.member.repository.MemberRepository
import com.tedmoon99.domain.member.repository.SignUpRepository
import com.tedmoon99.domain.member.usecase.SignUpUseCase
import com.tedmoon99.domain.member.usecase.SignUpUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MemberModule {

    @Provides
    @Singleton
    fun provideMemberRepository(
        dataStore: DataStore<Preferences>,
    ): MemberRepository {
        return MemberRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun providerSignUpService(@NetworkModule.BaseClient retrofit: Retrofit): SignUpService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideSignUpRepository(
        signUpService: SignUpService
    ): SignUpRepository = SignUpRepositoryImpl(signUpService)

    @Provides
    @Singleton
    fun provideSignUpUseCase(
        signUpRepository: SignUpRepository
    ): SignUpUseCase {
        return SignUpUseCaseImpl(signUpRepository)
    }
}