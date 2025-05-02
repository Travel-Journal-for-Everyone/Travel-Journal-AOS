package com.tedmoon99.trave_journal_for_everyone.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tedmoon99.data.search.datasource.SearchService
import com.tedmoon99.data.search.repository.SearchRepositoryImpl
import com.tedmoon99.domain.search.respository.SearchRepository
import com.tedmoon99.domain.search.usecase.SearchKeywordUseCase
import com.tedmoon99.domain.search.usecase.SearchKeywordUseCaseImpl
import com.tedmoon99.domain.search.usecase.SearchUseCase
import com.tedmoon99.domain.search.usecase.SearchUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    private val Context.searchDataStore: DataStore<Preferences> by preferencesDataStore(name = "search_prefs")

    @Provides
    @Singleton
    @Named("search_prefs")
    fun provideSearchDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.searchDataStore

    @Provides
    @Singleton
    fun providesSearchService(@NetworkModule.BaseClient retrofit: Retrofit): SearchService =
        retrofit.create()

    @Provides
    @Singleton
    fun providesSearchRepository(
        searchService: SearchService,
        @Named("search_prefs") dataStore: DataStore<Preferences>,
    ): SearchRepository {
        return SearchRepositoryImpl(searchService, dataStore)
    }

    @Provides
    @Singleton
    fun providesSearchUseCase(
        searchRepository: SearchRepository
    ): SearchUseCase {
        return SearchUseCaseImpl(searchRepository)
    }

    @Provides
    @Singleton
    fun providesSearchKeywordUseCase(
        searchRepository: SearchRepository,
    ): SearchKeywordUseCase {
        return SearchKeywordUseCaseImpl(searchRepository)
    }
}