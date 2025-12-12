package com.example.data.models

import com.example.database.models.CourseEntity
import com.example.domain.models.Course
import com.example.network.models.CourseResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toDate(): Date {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.parse(this)!!
}

fun CourseResponse.toCourse() = Course(
    id=id, title = title,text = text,price=price,rate=rate,
    startDate=startDate.toDate(),hasLike=hasLike,publishDate=publishDate.toDate())

fun CourseEntity.toCourse() = Course(
    id=id, title = title,text = text,price=price,rate=rate,
    startDate=Date(startDate),hasLike=hasLike,publishDate=Date(publishDate))

fun Course.toCourseEntity() = CourseEntity(
    id=id, title = title,text = text,price=price,rate=rate,
    startDate=startDate.time,hasLike=hasLike,publishDate=publishDate.time)