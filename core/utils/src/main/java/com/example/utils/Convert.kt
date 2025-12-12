package com.example.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toRussianFormat(): String {
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("ru"))
    return formatter.format(this)
}