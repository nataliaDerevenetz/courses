package com.example.domain.impl

import com.example.domain.SaveCourseAsFavoriteUseCase
import com.example.domain.models.Course
import com.example.domain.repository.CoursesRepository
import javax.inject.Inject

class SaveCourseAsFavoriteUseCaseImpl @Inject constructor(
    private val coursesRepository: CoursesRepository
): SaveCourseAsFavoriteUseCase {
    override suspend fun invoke(course: Course) {
        coursesRepository.saveCourseAsFavorite(course)
    }
}