package ru.sb066coder.diplonet.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

//    @Query("SELECT * FROM PostDbModel ORDER BY id DESC")
//    suspend fun getPostList(): Flow<List<PostDbModel>>

    @Query("SELECT * FROM PostDbModel WHERE id = :id")
    suspend fun getPostById(id: Int): PostDbModel?

    @Query("SELECT * FROM PostDbModel ORDER BY id DESC")
    fun getPagingSource(): PagingSource<Int, PostDbModel>

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<PostDbModel>)

    @Query("DELETE FROM PostDbModel")
    suspend fun clearTable()
}
