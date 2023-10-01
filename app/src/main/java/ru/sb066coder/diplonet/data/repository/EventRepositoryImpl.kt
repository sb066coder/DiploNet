package ru.sb066coder.diplonet.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.sb066coder.diplonet.data.api.ApiService
import ru.sb066coder.diplonet.domain.EventRepository
import ru.sb066coder.diplonet.domain.dto.Event
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor (
    private val apiService: ApiService,
    //private val iventDao: IventDao
) : EventRepository {
    override val data: Flow<PagingData<Event>>
        get() = TODO("Not yet implemented")

    override suspend fun getEventById(id: Int): Event {
        TODO("Not yet implemented")
    }

    override suspend fun addEvent(event: Event) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEventById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun editEvent(event: Event) {
        TODO("Not yet implemented")
    }

    override suspend fun likeEventById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun unlikeEventById(id: Int) {
        TODO("Not yet implemented")
    }
}