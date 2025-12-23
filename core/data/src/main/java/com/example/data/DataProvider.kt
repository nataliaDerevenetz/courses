package com.example.data

import com.example.data.models.toDate
import com.example.domain.models.Course

object DataProvider {

    val localCourses = listOf(
        Course(
            id = 100,
            title = "Java-разработчик с нуля",
            text = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
            price  = "999",
            rate = "4.9",
            startDate = "2024-05-22".toDate(),
            hasLike = false,
            publishDate = "2024-02-02".toDate()

        ),
        Course(
            id = 101,
            title = "3D-дженералист",
            text = "Освой профессию 3D-дженералиста и стань универсальным специалистом, который умеет создавать 3D-модели, текстуры и анимации, а также может строить карьеру в геймдеве, кино, рекламе или дизайне.",
            price  = "12 000",
            rate = "3.9",
            startDate = "2024-09-10".toDate(),
            hasLike = false,
            publishDate = "2024-01-20".toDate()

        ),

        Course(
            id = 102,
            title = "Python Advanced. Для продвинутых",
            text = "Вы узнаете, как разрабатывать гибкие и высокопроизводительные серверные приложения на языке Kotlin. Преподаватели на вебинарах покажут пример того, как разрабатывается проект маркетплейса: от идеи и постановки задачи – до конечного решения",
            price  = "1 299",
            rate = "4.3",
            startDate = "2024-10-12".toDate(),
            hasLike = false,
            publishDate = "2024-08-10".toDate()

        ),
    )

}