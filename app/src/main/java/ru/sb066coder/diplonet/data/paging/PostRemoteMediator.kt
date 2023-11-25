package ru.sb066coder.diplonet.data.paging

import android.util.Log
import androidx.paging.*
import retrofit2.HttpException
import ru.sb066coder.diplonet.data.api.ApiService
import ru.sb066coder.diplonet.data.database.PostDao
import ru.sb066coder.diplonet.data.database.PostDbModel
import ru.sb066coder.diplonet.data.database.fromDto
import ru.sb066coder.diplonet.domain.dto.Post
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val apiService: ApiService,
    private val postDao: PostDao
) : RemoteMediator<Int, PostDbModel>() {
        override suspend fun load(loadType: LoadType, state: PagingState<Int, PostDbModel>): MediatorResult {
        try {
            val result = when (loadType) {
                LoadType.REFRESH -> apiService.getLatest(state.config.pageSize)
                LoadType.PREPEND -> {
                    val id = state.firstItemOrNull()?.id ?: return MediatorResult.Success(false)
                    apiService.getAfter(id, state.config.pageSize)
                }
                LoadType.APPEND -> {
                    val id = state.lastItemOrNull()?.id ?: return MediatorResult.Success(false)
                    apiService.getBefore(id, state.config.pageSize)
                }
            }

            if (!result.isSuccessful) {
                throw HttpException(result)
            }

            val data = result.body().orEmpty()
            if (data.isNotEmpty()) {
                postDao.insert(data.fromDto())
            }
            Log.d("PostPagingSource", "data size: ${data.firstOrNull()?.id}")
            return MediatorResult.Success(data.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        }
    }

}