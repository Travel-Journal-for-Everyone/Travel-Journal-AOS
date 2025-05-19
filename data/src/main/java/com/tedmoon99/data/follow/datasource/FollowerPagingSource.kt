package com.tedmoon99.data.follow.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tedmoon99.data.common.utils.CommonUtils.Companion.STARTING_PAGE
import com.tedmoon99.data.follow.mapper.FollowerMapper
import com.tedmoon99.domain.follow.model.Follower
import okio.IOException
import retrofit2.HttpException

class FollowerPagingSource(
    private val followService: FollowService,
    private val memberId: Int,
    private val followerMapper: FollowerMapper
): PagingSource<Int, Follower>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Follower> {
        return try {
            val currentPage = params.key ?: STARTING_PAGE
            val followerList =
                followService.getFollowerList(memberId, currentPage + 1).body()?.followerEntities
                    ?: emptyList()

            LoadResult.Page(
                data = followerList.map { followerMapper.toDomain(it) },
                prevKey = if (currentPage == STARTING_PAGE) null else currentPage - 1,
                nextKey = if (followerList.isEmpty()) null else currentPage - 1
            )
        }catch (error: IOException) {
            Log.e(TAG, "IOException error: ${error.message}")
            LoadResult.Error(error)
        } catch (error: HttpException) {
            Log.e(TAG, "HTTPException error: ${error.message}")
            LoadResult.Error(error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Follower>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    companion object {
        private const val TAG = "FollowerPagingSource"
    }

}