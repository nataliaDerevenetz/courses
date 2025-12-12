package com.example.domain.repository

import com.example.domain.models.Course
import com.example.utils.UIResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CoursesRepository {
    suspend fun fetchCourses(): Flow<UIResources<List<Course>>>
    suspend fun saveCourseAsFavorite(course: Course)
    suspend fun removeCourseAsFavorite(course: Course)
    val favoritesUser: StateFlow<List<Course>>
}