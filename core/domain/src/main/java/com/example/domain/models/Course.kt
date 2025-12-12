package com.example.domain.models

import androidx.compose.runtime.Stable
import java.util.Date

@Stable
data class Course(
    val id: Int = 0,
    val title: String = "",
    val text: String = "",
    val price: String = "",
    val rate: String = "",
    val startDate: Date = Date(),
    val hasLike: Boolean = false,
    val publishDate: Date = Date()
)