package com.tedmoon99.data.token.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tedmoon99.data.common.secret.datasource.CryptoHelper
import com.tedmoon99.data.common.secret.mapper.SecretMapper
import com.tedmoon99.domain.token.repository.TokenRepository
import kotlinx.coroutines.flow.first

import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
): TokenRepository {

    override suspend fun setAccessToken(accessToken: String) {
        val secretKey = CryptoHelper.generateSecretKey()
        // 암호화 키
        val encryptedAccessToken = CryptoHelper.encrypt(accessToken, secretKey)
        // 암호화 해독 키
        val secretKeyBase64 = SecretMapper.fromSecretKey(secretKey)

        // 저장
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = encryptedAccessToken
            prefs[ACCESS_TOKEN_SECRET_KEY] = secretKeyBase64
        }
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        val secretKey = CryptoHelper.generateSecretKey()
        // 암호화 키
        val encryptedRefreshToken = CryptoHelper.encrypt(refreshToken, secretKey)
        // 암호화 해독 키
        val secretKeyBase64 = SecretMapper.fromSecretKey(secretKey)

        // 저장
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = encryptedRefreshToken
            prefs[ACCESS_TOKEN_SECRET_KEY] = secretKeyBase64
        }
    }

    override suspend fun setDeviceId(deviceId: String) {
        dataStore.edit { it[DEVICE_ID_KEY] = deviceId }
    }

    override suspend fun getAccessToken(): String? {
        var accessToken: String? = null
        val preferences = dataStore.data.first()
        // 암호화 키
        val encryptedAccessKey = preferences[ACCESS_TOKEN_KEY]
        // 암호화 해독 키
        val secretKeyBase64 = preferences[ACCESS_TOKEN_SECRET_KEY]

        if (encryptedAccessKey != null && secretKeyBase64 != null) {
            // Secret 키 복원
            val secretKey = SecretMapper.toSecretKey(secretKeyBase64)

            accessToken = CryptoHelper.decrypt(encryptedAccessKey, secretKey)
        }
        return accessToken
    }

    override suspend fun getRefreshToken(): String? {
        var refreshToken: String? = null
        val preferences = dataStore.data.first()
        // 암호화 키
        val encryptedRefreshKey = preferences[REFRESH_TOKEN_KEY]
        // 암호화 해독 키
        val secretKeyBase64 = preferences[REFRESH_TOKEN_SECRET_KEY]

        if (encryptedRefreshKey != null && secretKeyBase64 != null) {
            // Secret 키 복원
            val secretKey = SecretMapper.toSecretKey(secretKeyBase64)

            refreshToken = CryptoHelper.decrypt(encryptedRefreshKey, secretKey)
        }
        return refreshToken
    }

    override suspend fun getDeviceId(): String? {
        return dataStore.data.first()[DEVICE_ID_KEY]
    }


    override suspend fun deleteTokens() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
            prefs.remove(ACCESS_TOKEN_SECRET_KEY)
            prefs.remove(REFRESH_TOKEN_KEY)
            prefs.remove(REFRESH_TOKEN_SECRET_KEY)
            prefs.remove(DEVICE_ID_KEY)
        }
    }


    companion object {
        private const val TAG = "TokenRepositoryImpl"
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("accessToken")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refreshToken")
        private val DEVICE_ID_KEY = stringPreferencesKey("deviceId")
        private val ACCESS_TOKEN_SECRET_KEY = stringPreferencesKey("accessTokenSecretKey")
        private val REFRESH_TOKEN_SECRET_KEY = stringPreferencesKey("refreshTokenSecretKey")
    }
}