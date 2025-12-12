package com.example.domain

import com.example.domain.models.Course

interface RemoveCourseAsFavoriteUseCase {
    suspend operator fun invoke(course: Course)
}