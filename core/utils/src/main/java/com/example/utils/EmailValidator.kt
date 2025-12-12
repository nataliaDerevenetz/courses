package com.example.utils

import java.util.regex.Pattern

object EmailValidator {
    fun isValidEmail(email: String): Boolean {
        // Регулярное выражение для email с запретом кириллицы
        val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
        val pattern = Pattern.compile(emailPattern)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }
}