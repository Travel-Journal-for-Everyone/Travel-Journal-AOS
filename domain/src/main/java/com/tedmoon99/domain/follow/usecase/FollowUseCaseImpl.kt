package com.tedmoon99.domain.follow.usecase

import androidx.paging.PagingData
import com.tedmoon99.domain.follow.model.FollowCount
import com.tedmoon99.domain.follow.model.FollowRequest
import com.tedmoon99.domain.follow.model.Follower
import com.tedmoon99.domain.follow.model.Following
import com.tedmoon99.domain.follow.repository.FollowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FollowUseCaseImpl @Inject constructor(
    private val followRepository: FollowRepository
) : FollowUseCase {

    override suspend fun isFollowing(memberId: Int): Boolean = followRepository.isFollowing(memberId)

    override suspend fun getFollowCount(): FollowCount {
        return followRepository.getFollowCount()
    }

    override suspend fun denyFollowRequest(followId: Int) {
        followRepository.denyFollowRequest(followId)
    }

    override suspend fun approveFollowRequest(followId: Int) {
        followRepository.approveFollowRequest(followId)
    }

    override suspend fun getFollowingList(): Flow<PagingData<Following>> {
        return followRepository.getFollowingList()
    }

    override suspend fun getFollowerList(): Flow<PagingData<Follower>> {
        return followRepository.getFollowerList()
    }

    override suspend fun getFollowRequestList(): Flow<PagingData<FollowRequest>> {
        return  followRepository.getFollowRequestList()
    }

    // Following 삭제
    override suspend fun deleteFollowingItem(memberId: Int) {
        followRepository.unfollow(memberId)
    }

    // Follower 삭제
    override suspend fun deleteFollowerItem(memberId: Int) {
        TODO("Not yet implemented")
    }
}