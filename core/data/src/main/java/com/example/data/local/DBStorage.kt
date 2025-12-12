package com.example.data.local

import com.example.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface DBStorage {
    fun getFavorites(): Flow<List<Course>>
    suspend fun insertCourseFavorite(course: Course)
    suspend fun deleteCourseFavorite(course: Course)
}