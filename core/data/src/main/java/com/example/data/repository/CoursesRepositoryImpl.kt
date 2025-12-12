package com.example.data.repository

import com.example.data.local.DBStorage
import com.example.data.models.toCourse
import com.example.domain.models.Course
import com.example.domain.repository.CoursesRepository
import com.example.network.CoursesService
import com.example.utils.Constant
import com.example.utils.UIResources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val dbStorage : DBStorage,
    private val coursesService: CoursesService
): CoursesRepository {

    private val _favoritesUser = MutableStateFlow<List<Course>>(listOf())
    override val favoritesUser: StateFlow<List<Course>> get() = _favoritesUser

    override suspend fun fetchCourses(): Flow<UIResources<List<Course>>> = flow {
        try {
            emit(UIResources.Loading)
            val result = coursesService.downloadFile(Constant.BASE_URL)
            if (result.isSuccessful) {
                val courses = result.body()?.courses?.map {
                    it.toCourse()
                }
                if (!courses.isNullOrEmpty()) {
                    emit(UIResources.Success(courses))
                } else {
                    emit(UIResources.Error("Failed to fetch courses data"))
                }
            } else {
                emit(UIResources.Error("Failed to fetch courses data"))
            }
        } catch (e: Exception) {
            emit(UIResources.Error(e.message.toString()))
        }
    }

    override suspend fun saveCourseAsFavorite(course: Course) {
        dbStorage.insertCourseFavorite(course)
        _favoritesUser.update { currentList ->
            if (currentList.any { it.id == course.id }) {
                currentList
            } else {
                currentList + course
            }
        }
    }

    override suspend fun removeCourseAsFavorite(course: Course) {
        dbStorage.deleteCourseFavorite(course)
        _favoritesUser.update { currentList ->
            currentList.filterNot { it.id == course.id  }
        }
    }

    init {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val favorites = dbStorage.getFavorites().firstOrNull()
            if (favorites != null) {
                _favoritesUser.update { favorites }
            }
        }

    }
}