package com.tedmoon99.data.common.secret

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tedmoon99.data.common.secret.datasource.CryptoHelper
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.crypto.SecretKey

@RunWith(AndroidJUnit4::class)
class CryptoHelperTest {

    private var secretKey: SecretKey? = null

    @Before
    fun setUp() {
        secretKey = CryptoHelper.generateSecretKey()
    }

    @After
    fun tearDown() {
        secretKey = null
    }

    @Test
    fun encryptTest_O() {
        // Given
        val data = "testData"

        // When
        val encryptedKey = CryptoHelper.encrypt(data, secretKey!!)
        println("encryptedKey = ${encryptedKey}")

        // Then
        assertNotEquals(data, encryptedKey)
    }

    @Test
    fun decryptTest_O() {
        // Given
        val data = "testData"
        val encryptedKey = CryptoHelper.encrypt(data, secretKey!!)

        // When
        println("encryptedKey = ${encryptedKey}")
        val decryptedKey = CryptoHelper.decrypt(encryptedKey, secretKey!!)

        // Then
        assertEquals(data, decryptedKey)
    }

    @Test
    fun decryptTest_X() {
        // Given
        val data = "testData"
        val unExpectedData = "testData12"

        val encryptedKey = CryptoHelper.encrypt(data, secretKey!!)

        // When
        println("encryptedKey = ${encryptedKey}")
        val decryptedKey = CryptoHelper.decrypt(encryptedKey, secretKey!!)

        // Then
        assertNotEquals(unExpectedData, decryptedKey)
    }

}