package com.example.domain.impl

import com.example.domain.GetCoursesAllUseCase
import com.example.domain.models.Course
import com.example.domain.repository.CoursesRepository
import com.example.utils.UIResources
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoursesAllUseCaseImpl @Inject constructor(
    private val coursesRepository: CoursesRepository
): GetCoursesAllUseCase {
    override suspend operator fun invoke(): Flow<UIResources<List<Course>>> {
        return coursesRepository.fetchCourses()
    }
}