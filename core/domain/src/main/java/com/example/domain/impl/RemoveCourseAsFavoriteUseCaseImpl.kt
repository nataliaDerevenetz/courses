package com.example.domain.impl

import com.example.domain.RemoveCourseAsFavoriteUseCase
import com.example.domain.models.Course
import com.example.domain.repository.CoursesRepository
import javax.inject.Inject


class RemoveCourseAsFavoriteUseCaseImpl @Inject constructor(
    private val coursesRepository: CoursesRepository
): RemoveCourseAsFavoriteUseCase {
    override suspend fun invoke(course: Course) {
        coursesRepository.removeCourseAsFavorite(course)
    }
}