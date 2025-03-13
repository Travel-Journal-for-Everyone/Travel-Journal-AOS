package com.tedmoon99.data.member.repository

import android.util.Log
import com.tedmoon99.data.member.datasource.SignUpService
import com.tedmoon99.data.member.mapper.SignUpMapper
import com.tedmoon99.data.member.model.DuplicatedCheckResult
import com.tedmoon99.domain.member.entity.DuplicatedCheckResultEntity
import com.tedmoon99.domain.member.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val signUpService: SignUpService,
) : SignUpRepository {
    // 닉네임 중복체크
    override suspend fun checkDuplicatedName(name: String): DuplicatedCheckResultEntity {
        val response = signUpService.checkDuplicatedName(name)
        val result = when (response.code()) {
            200 -> {
                DuplicatedCheckResult(
                    success = true,
                    isDuplicated = false,
                    isBadWorld = false
                )
            }

            409 -> {
                val responseBody = response.body()
                DuplicatedCheckResult(
                    success = responseBody?.success ?: false,
                    isDuplicated = responseBody?.message == DUPLICATED,
                    isBadWorld = responseBody?.message == CONTAINS_BAD_WORLD
                )
            }

            else -> {
                DuplicatedCheckResult(success = false, isDuplicated = false, isBadWorld = false)
            }
        }
        Log.d(TAG, "중복확인 결과: $result")
        return SignUpMapper.toDomain(result)
    }

    companion object {
        private const val TAG = "SignUpRepositoryImpl"
        private const val DUPLICATED = "duplicate"
        private const val CONTAINS_BAD_WORLD = "containsBadWorld"

    }
}