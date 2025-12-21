package com.example.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.RemoveCourseAsFavoriteUseCase
import com.example.domain.SaveCourseAsFavoriteUseCase
import com.example.domain.models.Course
import com.example.domain.repository.CoursesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FavoriteUiEvent {
    data class OnSaveFavoriteCourse(val course: Course): FavoriteUiEvent()
    data class OnRemoveFavoriteCourse(val course: Course): FavoriteUiEvent()
}


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val saveCourseAsFavoriteUseCase: SaveCourseAsFavoriteUseCase,
    val removeCourseAsFavoriteUseCase: RemoveCourseAsFavoriteUseCase,
    coursesRepository: CoursesRepository,
): ViewModel()  {

    val favoritesUser = coursesRepository.favoritesUser

    fun handleEvent(event: FavoriteUiEvent) = viewModelScope.launch(Dispatchers.IO){
        when (event) {
            is FavoriteUiEvent.OnSaveFavoriteCourse -> {
                saveFavoriteCourse(event.course)
            }

            is FavoriteUiEvent.OnRemoveFavoriteCourse -> {
                removeFavoriteCourse(event.course)
            }
        }
    }

    private suspend fun saveFavoriteCourse(course: Course) {
        saveCourseAsFavoriteUseCase(course)
    }

    private suspend fun removeFavoriteCourse(course: Course) {
        removeCourseAsFavoriteUseCase(course)
    }
}
