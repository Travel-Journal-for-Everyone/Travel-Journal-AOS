package com.tedmoon99.data.common.secret.mapper

import android.util.Base64
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object SecretMapper {

    fun fromSecretKey(secretKey: SecretKey): String {
        return Base64.encodeToString(secretKey.encoded, Base64.DEFAULT)
    }

    fun toSecretKey(encodedKey: String): SecretKey {
        val decodedKey = Base64.decode(encodedKey, Base64.DEFAULT)
        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }
}