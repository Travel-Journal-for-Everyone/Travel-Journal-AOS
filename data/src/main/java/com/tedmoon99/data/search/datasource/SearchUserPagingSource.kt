package com.tedmoon99.data.search.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tedmoon99.data.common.utils.CommonUtils.Companion.STARTING_PAGE
import com.tedmoon99.data.search.mapper.SearchedUserMapper
import com.tedmoon99.domain.search.model.SearchResultItem
import retrofit2.HttpException
import java.io.IOException

class SearchUserPagingSource(
    private val searchService: SearchService,
    private val keyword: String,
    private val mapper: SearchedUserMapper
) : PagingSource<Int, SearchResultItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultItem> {
        return try {
            val currentPage = params.key ?: STARTING_PAGE
            val searchedUsers = searchService.searchUser(keyword, currentPage + 1).body()?.userList ?: emptyList()
            LoadResult.Page(
                data = searchedUsers.map { mapper.toDomain(it) },
                prevKey = if (currentPage == STARTING_PAGE) null else currentPage - 1,
                nextKey = if (searchedUsers.isEmpty()) null else currentPage + 1
            )
        } catch (error: IOException) {
            Log.e(TAG, "IOException error: ${error.message}")
            LoadResult.Error(error)
        } catch (error: HttpException) {
            Log.e(TAG, "HTTPException error: ${error.message}")
            LoadResult.Error(error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchResultItem>): Int? {
        return state.anchorPosition
    }

    companion object {
        private const val TAG = "SearchUserPagingSource"
    }
}