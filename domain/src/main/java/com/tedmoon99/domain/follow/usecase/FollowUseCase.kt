package com.tedmoon99.domain.follow.usecase

import androidx.paging.PagingData
import com.tedmoon99.domain.follow.model.FollowCount
import com.tedmoon99.domain.follow.model.FollowRequest
import com.tedmoon99.domain.follow.model.Follower
import com.tedmoon99.domain.follow.model.Following
import kotlinx.coroutines.flow.Flow

interface FollowUseCase {

    suspend fun isFollowing(memberId: Int): Boolean

    suspend fun getFollowCount(): FollowCount

    suspend fun denyFollowRequest(followId: Int)

    suspend fun approveFollowRequest(followId: Int)

    suspend fun getFollowingList(): Flow<PagingData<Following>>

    suspend fun getFollowerList(): Flow<PagingData<Follower>>

    suspend fun getFollowRequestList(): Flow<PagingData<FollowRequest>>

    suspend fun deleteFollowingItem(memberId: Int)

    suspend fun deleteFollowerItem(memberId: Int)

}