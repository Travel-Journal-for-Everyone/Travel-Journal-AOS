package com.tedmoon99.data.common.secret

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tedmoon99.data.common.secret.mapper.SecretMapper
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SecretMapperTest {

    private var accessToken: String? = null

    @After
    fun tearDown() {
        accessToken = null
    }

    @Test
    fun toSecretTest_O() {
        // Given
        accessToken = "test_token_12"

        // When
        val secretKey = SecretMapper.toSecretKey(accessToken!!)

        // Then
        assertNotEquals(secretKey, accessToken)
    }
}