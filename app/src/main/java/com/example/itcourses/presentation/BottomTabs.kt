package com.example.itcourses.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.favorite.navigation.FavoriteBaseRoute
import com.example.itcourses.R
import com.example.main.navigation.MainBaseRoute
import com.example.profile.ProfileBaseRoute
import com.example.utils.navigation.BottomRoute

enum class BottomTabs(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    val route: BottomRoute
) {

    MAIN(
        R.string.main,
        R.drawable.outline_main,
        MainBaseRoute
    ),
    FAVORITES(
        R.string.favorites,
        R.drawable.outline_favorites,
        FavoriteBaseRoute
    ),
    PROFILE(
        R.string.profile,
        R.drawable.outline_profile,
        ProfileBaseRoute
    );

}