package com.tedmoon99.trave_journal_for_everyone.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tedmoon99.data.member.repository.MemberRepositoryImpl
import com.tedmoon99.domain.member.repository.MemberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}