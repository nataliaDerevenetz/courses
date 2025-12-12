package com.example.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val text: String,
    @ColumnInfo val price: String,
    @ColumnInfo val rate: String,
    @ColumnInfo val startDate: Long,
    @ColumnInfo val hasLike: Boolean,
    @ColumnInfo val publishDate: Long,
)