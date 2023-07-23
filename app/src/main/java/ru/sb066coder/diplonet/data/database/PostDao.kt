package ru.sb066coder.diplonet.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM PostDbModel ORDER BY id DESC")
    suspend fun getPostList(): List<PostDbModel>

    @Query("SELECT * FROM PostDbModel WHERE id = :id")
    suspend fun getPostById(id: Int): PostDbModel

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<PostDbModel>)

    @Query("DELETE FROM PostDbModel")
    suspend fun clearTable()

}
