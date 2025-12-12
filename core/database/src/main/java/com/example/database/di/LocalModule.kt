package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.db.CoursesRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalSourceModuleProvider {
    @Provides
    fun provideCourseDao(database: CoursesRoomDatabase) =
        database.courseDao()

    @Provides
    @Singleton
    fun providesLocalDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CoursesRoomDatabase::class.java,
        "courses-database"
    ).build()
}