package com.tedmoon99.trave_journal_for_everyone.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tedmoon99.data.token.datasource.TokenAuthenticator
import com.tedmoon99.data.token.datasource.TokenInterceptor
import com.tedmoon99.data.token.datasource.TokenService
import com.tedmoon99.data.token.repository.TokenRepositoryImpl
import com.tedmoon99.domain.token.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Authenticator
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideTokenService(@NetworkModule.AuthClient retrofit: Retrofit): TokenService = retrofit.create()

    @Provides
    @Singleton
    fun provideTokenRepository(dataStore: DataStore<Preferences>): TokenRepository = TokenRepositoryImpl(dataStore)

    @Provides
    @Singleton
    @IntoSet
    fun provideTokenInterceptor(tokenRepository: TokenRepository): Interceptor = TokenInterceptor(tokenRepository)

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        tokenService: TokenService,
        tokenRepository: TokenRepository,
    ): Authenticator = TokenAuthenticator(tokenService, tokenRepository)

}