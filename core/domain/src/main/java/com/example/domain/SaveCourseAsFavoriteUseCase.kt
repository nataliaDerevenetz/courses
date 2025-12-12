package com.example.domain

import com.example.domain.models.Course

interface SaveCourseAsFavoriteUseCase {
    suspend operator fun invoke(course: Course)
}