package com.tedmoon99.data.common.secret.datasource

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object CryptoHelper {
    private const val AES_MODE = "AES/GCM/NoPadding"
    private const val TAG_LENGTH = 128
    private const val IV_SIZE = 12

    fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256) // AES 256-bit 키 생성
        return keyGenerator.generateKey()
    }

    fun encrypt(data: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(AES_MODE)
        val iv = ByteArray(IV_SIZE).apply { SecureRandom().nextBytes(this) }
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH, iv))
        val encryptedData = cipher.doFinal(data.toByteArray())

        return Base64.encodeToString(iv + encryptedData, Base64.DEFAULT)
    }

    fun decrypt(encryptedData: String, secretKey: SecretKey): String {
        val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)
        val iv = decodedData.copyOfRange(0, IV_SIZE)
        val cipherText = decodedData.copyOfRange(IV_SIZE, decodedData.size)

        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH, iv))
        val decryptedData = cipher.doFinal(cipherText)

        return String(decryptedData)
    }
}