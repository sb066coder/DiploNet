package ru.sb066coder.diplonet.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.sb066coder.diplonet.data.database.AppDatabase
import ru.sb066coder.diplonet.data.database.PostDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext
        context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, DB_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providesPostDao(
        appDatabase: AppDatabase
    ): PostDao = appDatabase.appDao()

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "diplo_net.db"
    }
}