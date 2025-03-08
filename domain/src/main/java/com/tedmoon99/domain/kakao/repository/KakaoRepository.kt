package com.tedmoon99.domain.kakao.repository

import com.tedmoon99.domain.kakao.entity.KakaoSignInResultEntity
import com.tedmoon99.domain.kakao.entity.KakaoSignOutResultEntity

interface KakaoRepository {
    fun isKakaoSignIn(): Boolean

    suspend fun requestKakaoSignIn(): KakaoSignInResultEntity

    suspend fun requestKakaoSignOut(): KakaoSignOutResultEntity
}