package com.tedmoon99.data.follow.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tedmoon99.data.common.utils.CommonUtils.Companion.STARTING_PAGE
import com.tedmoon99.data.follow.mapper.FollowingMapper
import com.tedmoon99.domain.follow.model.Following
import okio.IOException
import retrofit2.HttpException

class FollowingPagingSource(
    private val followService: FollowService,
    private val memberId: Int,
    private val followingMapper: FollowingMapper
): PagingSource<Int, Following>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Following> {
        return try {
            val currentPage = params.key ?: STARTING_PAGE
            val followingList = followService.getFollowingList(memberId, currentPage+1).body()?.followingEntities ?: emptyList()

            LoadResult.Page(
                data = followingList.map { followingMapper.toDomain(it) },
                prevKey = if (currentPage == STARTING_PAGE) null else currentPage - 1,
                nextKey = if (followingList.isEmpty()) null else currentPage + 1
            )
        } catch (error: IOException) {
            Log.e(TAG, "IOException error: ${error.message}")
            LoadResult.Error(error)
        } catch (error: HttpException) {
            Log.e(TAG, "HTTPException error: ${error.message}")
            LoadResult.Error(error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Following>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    companion object {
        private const val TAG = "FollowingPagingSource"
    }
}