package com.example.design

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.Job

@Composable
fun GlassyBox(
    modifier: Modifier = Modifier,
    left: () -> Dp,
    top: Dp = 30.dp,
    width: Dp = 200.dp,
    height: Dp = 60.dp,
    cornerRadius: Dp = 0.dp,
    @DrawableRes image: Int,
    isClicked: Boolean = false,
    onClick: () -> Job = { Job() },
    content: @Composable (() -> Unit)
) {
    val density = LocalDensity.current
    var defaultModifier = modifier
        .clip(GenericShape { size, layoutDirection ->
            val left = with(density) { left().toPx() }
            val top = with(density) { top.toPx() }
            val width = with(density) { width.toPx() }
            val height = with(density) { height.toPx() }
            val radius = with(density) { cornerRadius.toPx() }
            addRoundRect(
                RoundRect(
                    rect = Rect(left, top, left + width, top + height),
                    radiusX = radius,
                    radiusY = radius
                )
            )
        })

    if (isClicked) defaultModifier =defaultModifier.then(Modifier.clickable{
        onClick()
    })

    Box(
        modifier = defaultModifier
    ) {

        AsyncImage(
            model = image,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 16.dp),
            contentScale = ContentScale.FillWidth
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.gray).copy(alpha = 0.3f))
        )

        content()
    }
}

fun Modifier.cardModifier(): Modifier = this
    .fillMaxWidth()
    .height(114.dp)
    .clip(RoundedCornerShape(12.dp))

@Composable
fun BackdropBlurScreen(rate: String, date: String, hasLike: Boolean, handlerEvent: () -> Job, @DrawableRes image: Int) {
    val density = LocalDensity.current
    Box(modifier = Modifier.fillMaxWidth()) {

        // oсновная картинка
        val imageWidthPx = remember { mutableIntStateOf(0) }

        val widthInDpImage by remember(density) {
            derivedStateOf {
                with(density) { imageWidthPx.intValue.toDp() }
            }
        }

        AsyncImage(
            model = image,
            contentDescription = "",
            modifier = Modifier.cardModifier().onSizeChanged({ newSize ->
                if (imageWidthPx.intValue != newSize.width) {
                    imageWidthPx.intValue = newSize.width
                }}),
            contentScale = ContentScale.FillWidth
        )

        // секция рейтинга
        var widthRate = 0.dp
        SubcomposeLayout { constraints ->
            val placeableRate = subcompose("measure_rate") {
                BoxRate(rate)
            }.first().measure(constraints)

            widthRate = with(density) { placeableRate.width.toDp() + 6.dp }

            val mainPlaceable = subcompose("rate_content") {
                GlassyBox(
                    modifier = Modifier.cardModifier(),
                    left = {8.dp},
                    top = 80.dp,
                    width = widthRate,
                    height = 22.dp + 6.dp,
                    cornerRadius = 16.dp,
                    image = image
                ) {
                    Box(
                        modifier = Modifier
                            //left top
                            .offset(x = 8.dp, y = 80.dp)
                            .width(widthRate)
                            .height(22.dp + 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        BoxRate(rate)
                    }
                }
            }.first().measure(constraints)

            layout(mainPlaceable.width, mainPlaceable.height) {
                mainPlaceable.placeRelative(0, 0)
            }
        }

        // секция даты
        SubcomposeLayout { constraints ->
            val placeableDate = subcompose("measure_date") {
                BoxStartDate(date)
            }.first().measure(constraints)


            val width = with(density) { placeableDate.width.toDp() + 6.dp }

            val mainPlaceable = subcompose("date_content") {
                GlassyBox(
                    modifier = Modifier.cardModifier(),
                    left = {8.dp + widthRate + 8.dp},
                    top = 80.dp,
                    width = width,
                    height = 22.dp + 6.dp,
                    cornerRadius = 16.dp,
                    image = image,
                ) {
                    Box(
                        modifier = Modifier
                            //left top
                            .offset {
                                IntOffset(
                                    x = ( 8.dp + widthRate + 8.dp).roundToPx(),
                                    y = 80.dp.roundToPx()
                                )
                            }
                            .width(width)
                            .height(22.dp + 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        BoxStartDate(date)
                    }
                }
            }.first().measure(constraints)

            layout(mainPlaceable.width, mainPlaceable.height) {
                mainPlaceable.placeRelative(0, 0)
            }
        }

        // секция закладки
        GlassyBox(
            modifier = Modifier.cardModifier(),
            left = {widthInDpImage - 28.dp - 16.dp},
            top = 8.dp,
            width = 28.dp + 6.dp,
            height = 28.dp + 6.dp,
            cornerRadius = 16.dp,
            image = image,
            isClicked = true,
            onClick = handlerEvent
        ) {

            Box(
                modifier = Modifier
                    //left top
                    .offset {
                        IntOffset(
                            x = (widthInDpImage  - 28.dp - 16.dp).roundToPx(),
                            y = 8.dp.roundToPx()
                        )
                    }
                    .width(28.dp + 6.dp)
                    .height(28.dp + 6.dp),
                contentAlignment = Alignment.Center
            ) {
                BoxLike(hasLike)
            }
        }
    }
}

@Composable
private fun BoxLike(hasLike: Boolean) {
    Icon(
        imageVector = if(hasLike) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
        contentDescription = "",
        modifier = Modifier
            .size(16.dp),
        tint = if(hasLike) colorResource(R.color.green) else Color.White
    )
}

@Composable
private fun BoxRate(rate: String) {
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Color.Green
        )
        Text(
            text = rate,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 2.dp, end = 6.dp)
        )
    }
}

@Composable
private fun BoxStartDate(date: String) {
    Text(
        date,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 6.dp, end = 6.dp),
        maxLines = 1
    )
}

