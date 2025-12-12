package com.example.main.di

import com.example.domain.GetCoursesAllUseCase
import com.example.domain.RemoveCourseAsFavoriteUseCase
import com.example.domain.SaveCourseAsFavoriteUseCase
import com.example.domain.impl.GetCoursesAllUseCaseImpl
import com.example.domain.impl.RemoveCourseAsFavoriteUseCaseImpl
import com.example.domain.impl.SaveCourseAsFavoriteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainScreenModule {

    @ViewModelScoped
    @Binds
    abstract fun bindGetCoursesAllUseCase(
        getCoursesAllUseCaseImpl: GetCoursesAllUseCaseImpl
    ) : GetCoursesAllUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindSaveCourseAsFavoriteUseCase(
        saveCourseAsFavoriteUseCaseImpl: SaveCourseAsFavoriteUseCaseImpl
    ) : SaveCourseAsFavoriteUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindRemoveCourseAsFavoriteUseCase(
        saveCourseAsFavoriteUseCaseImpl: RemoveCourseAsFavoriteUseCaseImpl
    ) : RemoveCourseAsFavoriteUseCase

}