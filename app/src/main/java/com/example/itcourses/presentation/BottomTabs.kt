package com.example.itcourses.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.favorite.navigation.FavoriteBaseRoute
import com.example.itcourses.R
import com.example.favorite.navigation.FavoriteRoute
import com.example.main.navigation.MainBaseRoute
import com.example.profile.ProfileRoute
import com.example.main.navigation.MainRoute
import com.example.profile.ProfileBaseRoute
import kotlin.reflect.KClass

enum class BottomTabs(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    val route: KClass<*>
) {

    MAIN(
        R.string.main,
        R.drawable.outline_main,
        MainBaseRoute::class
    ),
    FAVORITES(
        R.string.favorites,
        R.drawable.outline_favorites,
        FavoriteBaseRoute::class
    ),
    PROFILE(
        R.string.profile,
        R.drawable.outline_profile,
        ProfileBaseRoute::class
    );

}