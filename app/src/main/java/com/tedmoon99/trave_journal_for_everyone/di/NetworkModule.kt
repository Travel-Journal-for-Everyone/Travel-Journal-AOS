package com.tedmoon99.trave_journal_for_everyone.di

import com.tedmoon99.data.BuildConfig.BASE_URL
import com.tedmoon99.data.token.datasource.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @IntoSet
    fun provideLoggingInterceptor(): Interceptor{
        return LoggingInterceptor()
    }

    // Auth 인증 필요O API
    @BaseClient
    @Provides
    @Singleton
    fun provideBaseClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
        tokenAuthenticator: Authenticator
    ): OkHttpClient {
        return OkHttpClient
            .Builder().run {
                interceptors.forEach { addInterceptor(it) }
                authenticator(tokenAuthenticator)
                connectTimeout(30, TimeUnit.SECONDS) // 서버 연결 대기 시간
                readTimeout(30, TimeUnit.SECONDS) // 서버 응답 대기 시간
                writeTimeout(30, TimeUnit.SECONDS) // 요청 데이터 전송 시간
                retryOnConnectionFailure(true)
                followRedirects(false)// 리다이렉션 방지
                followSslRedirects(false) // SSL 리다이렉션 방지
            }.build()
    }

    // Auth 인증 필요X API
    @AuthClient
    @Provides
    @Singleton
    fun provideAuthClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
        tokenAuthenticator: Authenticator
    ): OkHttpClient {
        return OkHttpClient
            .Builder().run {
                interceptors.forEach { addInterceptor(it) }
                authenticator(tokenAuthenticator)
                connectTimeout(30, TimeUnit.SECONDS) // 서버 연결 대기 시간
                readTimeout(30, TimeUnit.SECONDS) // 서버 응답 대기 시간
                writeTimeout(30, TimeUnit.SECONDS) // 요청 데이터 전송 시간
                retryOnConnectionFailure(true)
                followRedirects(false)// 리다이렉션 방지
                followSslRedirects(false) // SSL 리다이렉션 방지
            }.build()
    }

    // Auth 인증 필요O API
    @BaseClient
    @Provides
    @Singleton
    fun provideBaseRetrofit(
        @BaseClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Auth 인증 필요X API
    @AuthClient
    @Provides
    @Singleton
    fun provideAuthRetrofit(
        @AuthClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthClient
}