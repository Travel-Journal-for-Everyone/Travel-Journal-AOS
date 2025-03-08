package com.tedmoon99.domain.kakao.usecase

import com.tedmoon99.domain.kakao.entity.KakaoSignInResultEntity
import com.tedmoon99.domain.kakao.entity.KakaoSignOutResultEntity

interface KakaoUseCase {
    suspend fun requestKakaoSignIn(): KakaoSignInResultEntity

    suspend fun requestKakaoSignOut(): KakaoSignOutResultEntity
}