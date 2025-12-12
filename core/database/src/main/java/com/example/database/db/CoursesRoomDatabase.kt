package com.example.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.models.CourseEntity
import com.example.database.dao.CourseDao

@Database(entities = [CourseEntity::class], version = 1,exportSchema = false)
abstract class CoursesRoomDatabase: RoomDatabase() {
    abstract fun courseDao() : CourseDao
}