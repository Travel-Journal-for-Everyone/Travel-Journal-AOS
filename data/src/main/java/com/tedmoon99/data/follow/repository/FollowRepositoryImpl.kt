package com.tedmoon99.data.follow.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tedmoon99.data.common.utils.CommonUtils.Companion.PAGING_SIZE
import com.tedmoon99.data.follow.datasource.FollowRequestPagingSource
import com.tedmoon99.data.follow.datasource.FollowService
import com.tedmoon99.data.follow.datasource.FollowerPagingSource
import com.tedmoon99.data.follow.datasource.FollowingPagingSource
import com.tedmoon99.data.follow.mapper.FollowCountMapper
import com.tedmoon99.data.follow.mapper.FollowRequestMapper
import com.tedmoon99.data.follow.mapper.FollowerMapper
import com.tedmoon99.data.follow.mapper.FollowingMapper
import com.tedmoon99.domain.follow.model.FollowCount
import com.tedmoon99.domain.follow.model.FollowRequest
import com.tedmoon99.domain.follow.model.Follower
import com.tedmoon99.domain.follow.model.Following
import com.tedmoon99.domain.follow.repository.FollowRepository
import com.tedmoon99.domain.member.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FollowRepositoryImpl @Inject constructor(
    private val followService: FollowService,
    private val memberRepository: MemberRepository
): FollowRepository {

    private suspend fun getMemberId(): Int {
        val memberId = memberRepository.getUserId()
        return memberId ?: run {
            val errorMessage = "잘못된 MemberId: null 반환됨"
            Log.e(TAG, errorMessage)
            throw IllegalStateException(errorMessage)
        }
    }

    override suspend fun follow(memberId: Int) {
        try {
            followService.follow(memberId = memberId)
        } catch (error: Exception) {
            Log.e(TAG, "follow 오류: ${error.message}")
        }
    }

    override suspend fun unfollow(memberId: Int) {
        try {
            followService.unfollow(memberId = memberId)
        } catch (error: Exception) {
            Log.e(TAG, "unfollow 오류: ${error.message}")
        }
    }

    override suspend fun denyFollowRequest(followId: Int) {
        try {
            followService.denyFollowRequest(followId = followId)
        } catch (error: Exception) {
            Log.e(TAG, "follow 요청 거절 오류: ${error.message}")
        }
    }

    override suspend fun approveFollowRequest(followId: Int) {
        try {
            followService.approveFollowRequest(followId = followId)
        } catch (error: Exception) {
            Log.e(TAG, "follow 요청 승인 오류: ${error.message}")
        }
    }

    override suspend fun isFollowing(memberId: Int): Boolean {
        return try {
            val response = followService.isFollowing(memberId)
            if (response.isSuccessful) {
                response.body() ?: false
            } else {
                Log.w(TAG, "follow 여부 확인 실패 - 응답 코드: ${response.code()}")
                false
            }
        } catch (error: Exception) {
            Log.e(TAG, "follow 여부 확인 오류", error)
            false
        }
    }

    override suspend fun getFollowingList(): Flow<PagingData<Following>> {

        val memberId =  try { getMemberId() } catch (error: Exception) { Log.e(TAG, "MemberId 조회 실패", error) }

        val pagingConfig = PagingConfig(pageSize = PAGING_SIZE, enablePlaceholders = false)

        val pagingSourceFactory = FollowingPagingSource(followService, memberId, FollowingMapper)

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { pagingSourceFactory }
        ).flow
    }

    override suspend fun getFollowerList(): Flow<PagingData<Follower>> {

        val memberId =  try { getMemberId() } catch (error: Exception) { Log.e(TAG, "MemberId 조회 실패", error) }

        val pagingConfig = PagingConfig(pageSize = PAGING_SIZE, enablePlaceholders = false)

        // FollowerPagingSource
        val pagingSourceFactory = FollowerPagingSource(followService, memberId, FollowerMapper)

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { pagingSourceFactory }
        ).flow
    }

    override suspend fun getFollowCount(): FollowCount {
        val memberId = try { getMemberId() } catch (error: Exception) { Log.e(TAG, "MemberId 조회 실패", error) }
        return try {
            val response = followService.getFollowCount(memberId)

            if (response.isSuccessful) {
                val body = response.body()
                FollowCountMapper.toDomain(body!!)
            } else {
                Log.w(TAG, "FollowCount 조회 실패")
                FollowCount(0,0)
            }
        } catch (error: Exception) {
            Log.e(TAG, "FollowCount 조회 오류: error: ${error.message}")
            FollowCount(0,0)
        }
    }

    override suspend fun getFollowRequestList(): Flow<PagingData<FollowRequest>> {
        val memberId =  try { getMemberId() } catch (error: Exception) { Log.e(TAG, "MemberId 조회 실패", error) }

        val pagingConfig = PagingConfig(pageSize = PAGING_SIZE, enablePlaceholders = false)

        // FollowRequestPagingSource
        val pagingSourceFactory = FollowRequestPagingSource(followService, memberId, FollowRequestMapper)

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { pagingSourceFactory }
        ).flow
    }

    companion object {
        private const val TAG = "FollowRepositoryImpl"
    }
}