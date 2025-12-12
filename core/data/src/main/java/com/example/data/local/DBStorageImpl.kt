package com.example.data.local

import com.example.data.models.toCourse
import com.example.data.models.toCourseEntity
import com.example.database.CourseDao
import com.example.domain.models.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DBStorageImpl @Inject constructor(
    private val courseDao: CourseDao,
): DBStorage {

    override fun getFavorites(): Flow<List<Course>> {
        return courseDao.getFavorites().map{
                course -> course.map { it.toCourse() }
        }
    }

    override suspend fun insertCourseFavorite(course: Course) {
        courseDao.insert(course.toCourseEntity())
    }

    override suspend fun deleteCourseFavorite(course: Course) {
        courseDao.delete(course.toCourseEntity())
    }
}

