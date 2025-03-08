package com.tedmoon99.data.member.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tedmoon99.domain.member.repository.MemberRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
): MemberRepository {
    override suspend fun setUserId(userId: Int) {
        dataStore.edit { it[MEMBER_ID_KEY] }
    }

    override suspend fun getUserId(): Int? {
        return dataStore.data.first()[MEMBER_ID_KEY]
    }

    companion object {
        private val TAG = "SignInRepositoryImpl"
        private val MEMBER_ID_KEY = intPreferencesKey("memberId")
        private val MEMBER_NAME_KEY = stringPreferencesKey("memberName")
        private val MEMBER_EMAIL_KEY = stringPreferencesKey("memberEmail")
        private val DEVICE_ID_KEY = stringPreferencesKey("deviceId")
    }
}