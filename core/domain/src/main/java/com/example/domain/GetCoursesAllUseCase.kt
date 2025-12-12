package com.example.domain

import com.example.domain.models.Course
import com.example.utils.UIResources
import kotlinx.coroutines.flow.Flow

interface GetCoursesAllUseCase {
    suspend operator fun invoke(): Flow<UIResources<List<Course>>>
}