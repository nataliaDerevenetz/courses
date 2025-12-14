package com.example.utils.ui

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.utils.R
import kotlinx.coroutines.Job

@Composable
fun GlassyBox(
    modifier: Modifier = Modifier,
    left: Dp = 100.dp,
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
            val left = with(density) { left.toPx() }
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
        var imageSize by remember { mutableStateOf(IntSize.Zero) }

        val widthInDpImage: Dp by remember(density, imageSize) {
            derivedStateOf {
                with(density) {
                    imageSize.width.toDp()
                }
            }
        }

        AsyncImage(
            model = image,
            contentDescription = "",
            modifier = Modifier.cardModifier().onSizeChanged({
                    size -> imageSize = size }),
            contentScale = ContentScale.FillWidth
        )

        // секция рейтинга
        val widthInPxRate = with(density) { 100.dp.roundToPx() }
        val heightInPxRate = with(density) { 22.dp.roundToPx()}

        val sizeInPxRate = IntSize(widthInPxRate, heightInPxRate)

        var childSizeRate by remember{ mutableStateOf(sizeInPxRate) }


        val widthInDpRate: Dp by remember(density, childSizeRate) {
            derivedStateOf {
                with(density) {
                    childSizeRate.width.toDp() + 6.dp
                }
            }
        }

        GlassyBox(
            modifier = Modifier.cardModifier(),
            left = 8.dp,
            top = 80.dp,
            width = widthInDpRate,
            height = 22.dp + 6.dp,
            cornerRadius = 16.dp,
            image = image
        ) {

            Box(
                modifier = Modifier
                    //left top
                    .offset(x = 8.dp, y = 80.dp)
                    .width(widthInDpRate)
                    .height(22.dp + 6.dp),
                contentAlignment = Alignment.Center
            ) {

                BoxRate(rate,onSizeChanged = { size ->
                    childSizeRate = size
                })
            }
        }


        // секция даты
        val widthInPxDate = with(density) { 100.dp.roundToPx() }
        val heightInPxDate = with(density) { 22.dp.roundToPx()}

        val sizeInPxDate = IntSize(widthInPxDate, heightInPxDate)

        var childSizeDate by remember (date){ mutableStateOf(sizeInPxDate) }


        val widthInDpDate: Dp by remember(density, childSizeDate) {
            derivedStateOf {
                with(density) {
                    childSizeDate.width.toDp() + 6.dp
                }
            }
        }

        GlassyBox(
            modifier = Modifier.cardModifier(),
            left = 8.dp + widthInDpRate + 8.dp,
            top = 80.dp,
            width = widthInDpDate,
            height = 22.dp + 6.dp,
            cornerRadius = 16.dp,
            image = image,
        ) {

            Box(
                modifier = Modifier
                    //left top
                    .offset(x = 8.dp + widthInDpRate + 8.dp, y = 80.dp) // ← ТАКИЕ ЖЕ отступы!
                    .width(widthInDpDate)
                    .height(22.dp + 6.dp),
                contentAlignment = Alignment.Center
            ) {

                BoxStartDate(date,onSizeChanged = { size ->
                    childSizeDate = size
                })
            }
        }

        // секция закладки
        GlassyBox(
            modifier = Modifier.cardModifier(),
            left = widthInDpImage - 28.dp - 16.dp,
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
                    .offset(x = widthInDpImage - 28.dp - 16.dp, y = 8.dp)
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
private fun BoxRate(rate: String,onSizeChanged: (IntSize) -> Unit = {}) {
    Row(modifier = Modifier.onSizeChanged(onSizeChanged),
        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "",
            modifier = Modifier
                .size(16.dp),
            tint = colorResource(R.color.green)
        )

        Text(
            rate,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top= 4.dp, bottom = 4.dp, start = 2.dp, end = 6.dp)
        )
    }
}

@Composable
private fun BoxStartDate(date: String,onSizeChanged: (IntSize) -> Unit = {}) {
    Box(modifier = Modifier.onSizeChanged(onSizeChanged)) {
        Text(
            date,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 6.dp, end = 6.dp),
            maxLines = 1
        )
    }
}
