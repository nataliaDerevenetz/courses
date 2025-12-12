package com.example.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetCoursesAllUseCase
import com.example.domain.RemoveCourseAsFavoriteUseCase
import com.example.domain.SaveCourseAsFavoriteUseCase
import com.example.domain.models.Course
import com.example.domain.repository.CoursesRepository
import com.example.utils.UIResources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


sealed interface LoadCourseState{
    object Idle: LoadCourseState
    object Loading: LoadCourseState
    data class  Success(val courses: List<Course>): LoadCourseState
    data class Error(val error: String): LoadCourseState
}

data class LoadCourseUIState(
    val contentState: LoadCourseState = LoadCourseState.Idle
)

sealed class MainUiEvent {
    data object OnLoadCourses: MainUiEvent()
    data class OnSaveFavoriteCourse(val course: Course): MainUiEvent()
    data class OnRemoveFavoriteCourse(val course: Course): MainUiEvent()
}


@HiltViewModel
class MainViewModel @Inject constructor(
    val getCoursesAllUseCase: GetCoursesAllUseCase,
    val saveCourseAsFavoriteUseCase: SaveCourseAsFavoriteUseCase,
    val removeCourseAsFavoriteUseCase: RemoveCourseAsFavoriteUseCase,
    coursesRepository: CoursesRepository,
    ): ViewModel()  {

    private val _stateLoadCourse = MutableStateFlow(LoadCourseUIState())
    val stateLoadCourse: StateFlow<LoadCourseUIState> = _stateLoadCourse.asStateFlow()

    val favoritesUser = coursesRepository.favoritesUser

    fun handleEvent(event: MainUiEvent) = viewModelScope.launch(Dispatchers.IO){
        when (event) {
            is MainUiEvent.OnLoadCourses -> {
                getCourses()
            }

            is MainUiEvent.OnSaveFavoriteCourse -> {
                saveFavoriteCourse(event.course)
            }

            is MainUiEvent.OnRemoveFavoriteCourse -> {
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

    private suspend fun getCourses() {
        _stateLoadCourse.update { it.copy(contentState = LoadCourseState.Idle) }
        getCoursesAllUseCase().collectLatest { resources ->
            when (resources) {
                is UIResources.Error -> withContext(Dispatchers.Main){
                    _stateLoadCourse.update {
                        it.copy(
                            contentState = LoadCourseState.Error(
                                resources.message
                            )
                        )
                    }
                }

                is UIResources.Loading -> withContext(Dispatchers.Main){
                    _stateLoadCourse.update { it.copy(contentState = LoadCourseState.Loading) }
                }

                is UIResources.Success -> withContext(Dispatchers.Main){
                    _stateLoadCourse.update { it.copy(contentState = LoadCourseState.Success(courses = resources.data)) }
                }
            }
        }
    }

        init {
            handleEvent(MainUiEvent.OnLoadCourses)
        }
}