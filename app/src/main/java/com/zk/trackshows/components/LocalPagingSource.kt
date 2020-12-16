package com.zk.trackshows.components

import androidx.paging.PagingSource
import com.zk.trackshows.common.InfoLogger.logMessage
import com.zk.trackshows.model.Show
import retrofit2.HttpException
import java.io.IOException

private const val TV_SHOWS_STARTING_PAGE_INDEX = 1


class LocalPagingSource(
    private val pagedDataFetcher: suspend (Int) -> List<Show>,
) : PagingSource<Int, Show>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Show> {
        val position = params.key ?: TV_SHOWS_STARTING_PAGE_INDEX
        return try {
            val shows = pagedDataFetcher(position)
            logMessage("Service -> fetchPopularShows: ${shows.size}")
            LoadResult.Page(
                data = shows,
                prevKey = if (position == TV_SHOWS_STARTING_PAGE_INDEX) null else position,
                nextKey = if (shows.isEmpty()) null else position + 1

            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}