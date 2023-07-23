package ru.sb066coder.diplonet.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.sb066coder.diplonet.data.api.ApiService
import ru.sb066coder.diplonet.data.database.PostDao
import ru.sb066coder.diplonet.data.database.fromDto
import ru.sb066coder.diplonet.data.database.toDto
import ru.sb066coder.diplonet.domain.dto.Post
import java.io.IOException

class PostPagingSource(
    private val apiService: ApiService,
    private val postDao: PostDao
) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        try {
            val result = when (params) {
                is LoadParams.Refresh -> {
                    apiService.getLatest(params.loadSize)
                }
                is LoadParams.Append -> {
                    apiService.getBefore(id = params.key, count = params.loadSize)
                }
                is LoadParams.Prepend -> return LoadResult.Page(
                    data = emptyList(), nextKey = null, prevKey = params.key,
                )
            }

            if (!result.isSuccessful) {
                throw HttpException(result)
            }

            val data = result.body().orEmpty()
            if (data.isNotEmpty()) {
                postDao.clearTable()
                postDao.insert(data.fromDto())
            }
            return LoadResult.Page(postDao.getPostList().toDto(), prevKey = params.key, nextKey = data.lastOrNull()?.id)
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
    }
}