package com.zk.trackshows.repository.network.api

import androidx.paging.PagingSource
import com.zk.trackshows.common.InfoLogger.logMessage
import com.zk.trackshows.repository.network.model.ShowDto
import retrofit2.HttpException
import java.io.IOException

private const val TV_SHOWS_STARTING_PAGE_INDEX = 1


class RemotePagingSource(
    private val pagedDataFetcher: suspend (Int) -> TvShowResponse,
) : PagingSource<Int, ShowDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShowDto> {
        val position = params.key ?: TV_SHOWS_STARTING_PAGE_INDEX
        return try {
            val response = pagedDataFetcher(position)
            val shows = response.shows
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