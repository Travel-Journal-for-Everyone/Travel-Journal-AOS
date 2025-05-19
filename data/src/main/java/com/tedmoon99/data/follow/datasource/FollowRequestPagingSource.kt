package com.tedmoon99.data.follow.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tedmoon99.data.common.utils.CommonUtils.Companion.STARTING_PAGE
import com.tedmoon99.data.follow.mapper.FollowRequestMapper
import com.tedmoon99.domain.follow.model.FollowRequest
import okio.IOException
import retrofit2.HttpException

class FollowRequestPagingSource(
    private val followService: FollowService,
    private val memberId: Int,
    private val followRequestMapper: FollowRequestMapper,
): PagingSource<Int, FollowRequest>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FollowRequest> {
        return try {
            val currentPage = params.key ?: STARTING_PAGE
            val followerList = followService.getFollowRequestList(currentPage + 1).body()?.followRequestEntities ?: emptyList()

            LoadResult.Page(
                data = followerList.map { followRequestMapper.toDomain(it) },
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

    override fun getRefreshKey(state: PagingState<Int, FollowRequest>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    companion object {
        private const val TAG = "FollowRequestPagingSource"
    }
}