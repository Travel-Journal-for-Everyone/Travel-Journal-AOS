package com.tedmoon99.data.member.repository

import android.util.Log
import com.tedmoon99.data.member.datasource.SignUpService
import com.tedmoon99.data.member.mapper.DuplicatedCheckMapper
import com.tedmoon99.data.member.mapper.SignUpMapper
import com.tedmoon99.data.member.model.DuplicatedCheckResult
import com.tedmoon99.domain.member.entity.DuplicatedCheckResultEntity
import com.tedmoon99.domain.member.entity.SignUpDtoEntity
import com.tedmoon99.domain.member.entity.SignUpResultEntity
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
                Log.d(TAG, "닉네임 중복체크 성공: ${response.body()}")
                DuplicatedCheckResult(
                    success = true,
                    isDuplicated = false,
                    isBadWorld = false
                )
            }

            409 -> {
                Log.d(TAG, "닉네임 중복체크 실패: ${response.body()}")
                DuplicatedCheckResult(
                    success = false,
                    isDuplicated = true,
                    isBadWorld = true
                )
            }

            else -> {
                DuplicatedCheckResult(success = false, isDuplicated = false, isBadWorld = false)
            }
        }
        Log.d(TAG, "중복확인 결과: $result")
        return DuplicatedCheckMapper.toDomain(result)
    }

    override suspend fun completeSignUp(request: SignUpDtoEntity): SignUpResultEntity {
        val dto = SignUpMapper.fromDomain(request)
        val response = signUpService.completeSignUp(dto)
        return if (response.isSuccessful && response.code() == 200) {
            val responseBody = response.body()
            if (responseBody != null) {
                Log.d(TAG, "회원 가입 완료 결과: $responseBody")
                SignUpResultEntity(
                    success = responseBody.success,
                    message = responseBody.message
                )
            } else{
                Log.d(TAG, "회원 가입 완료 결과: $responseBody")
                SignUpResultEntity(
                    success = false,
                    message = ""
                )
            }
        } else {
            Log.d(TAG, "회원 가입 완료 응답 실패코드: ${response.code()}")
            SignUpResultEntity(
                success = false,
                message = ""
            )
        }
    }

    companion object {
        private const val TAG = "SignUpRepositoryImpl"
        private const val DUPLICATED = "duplicate"
        private const val CONTAINS_BAD_WORLD = "containsBadWorld"

    }
}