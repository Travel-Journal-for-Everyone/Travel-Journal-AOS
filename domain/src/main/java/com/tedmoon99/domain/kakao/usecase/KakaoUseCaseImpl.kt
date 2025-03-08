package com.tedmoon99.domain.kakao.usecase

import com.tedmoon99.domain.kakao.entity.KakaoSignInResultEntity
import com.tedmoon99.domain.kakao.entity.KakaoSignOutResultEntity
import com.tedmoon99.domain.kakao.repository.KakaoRepository
import javax.inject.Inject

class KakaoUseCaseImpl @Inject constructor(
    private val kakaoRepository: KakaoRepository,
): KakaoUseCase {
    override suspend fun requestKakaoSignIn(): KakaoSignInResultEntity {
        return kakaoRepository.requestKakaoSignIn()
    }

    override suspend fun requestKakaoSignOut(): KakaoSignOutResultEntity {
        return kakaoRepository.requestKakaoSignOut()
    }
}