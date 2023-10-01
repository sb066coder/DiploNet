package ru.sb066coder.diplonet.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.sb066coder.diplonet.domain.dto.Event

interface EventRepository {
    val data: Flow<PagingData<Event>>
    suspend fun getEventById(id: Int): Event
    suspend fun addEvent(event: Event)
    suspend fun deleteEventById(id: Int)
    suspend fun editEvent(event: Event)
    suspend fun likeEventById(id: Int)
    suspend fun unlikeEventById(id: Int)
}