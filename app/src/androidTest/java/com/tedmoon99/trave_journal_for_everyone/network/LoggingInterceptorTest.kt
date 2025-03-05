package com.tedmoon99.trave_journal_for_everyone.network

import com.tedmoon99.trave_journal_for_everyone.di.NetworkModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class LoggingInterceptorTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var mockWebServer: MockWebServer


    @TestNetworkModule.BaseClient
    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp(){
        // hilt 주입
        hiltRule.inject()

        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun interceptorLogRequestAndResponse() {
        // Given: Mock 서버에서 200 응답을 반환
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("OK"))
        // When
        val request = Request.Builder()
            .url(mockWebServer.url("/test"))
            .addHeader("Authorization", "Bearer test-token")
            .build()

        val response = okHttpClient.newCall(request).execute()

        // Then
        assertNotNull(response)
        assertEquals(200, response.code)
        assertEquals("OK", response.body?.string())

        // 요청이 MockWebServer에 전달되었는지 검증
        val recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS)
        assertNotNull(recordedRequest)
        assertEquals("/test", recordedRequest?.path)
        assertEquals("Bearer test-token", recordedRequest?.getHeader("Authorization"))
    }
}