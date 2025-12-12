package com.example.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.Course
import com.example.utils.toRussianFormat
import com.example.utils.ui.BackdropBlurScreen
import kotlinx.coroutines.Job

@Composable
internal fun FavoriteScreen(viewModel: FavoriteViewModel = hiltViewModel())
{
    val favoritesUser by viewModel.favoritesUser.collectAsStateWithLifecycle()
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        if (favoritesUser.isEmpty()) {
            FavoriteScreenEmpty()
        } else {
            LazyColumn(Modifier.padding(innerPadding)) {
                items(items = favoritesUser){
                        course -> CardCourse({viewModel.handleEvent(FavoriteUiEvent.OnRemoveFavoriteCourse(course))},course)
                }
            }
        }
    }
}


@Composable
private fun FavoriteScreenEmpty()
{
    Box( modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(stringResource(R.string.not_favorite))
    }
}

@Composable
private fun CardCourse(handlerEvent: () -> Job, course: Course) {
    val coverImages = rememberSaveable() {
        listOf(
            R.drawable.cover1,
            R.drawable.cover2,
            R.drawable.cover3
        )
    }
    val randomIndex = rememberSaveable(course.id) {
        (0..2).random()
    }

    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.dark_gray),
        )
    ){
        Row(horizontalArrangement = Arrangement.SpaceBetween){
            Column(Modifier.padding(start =  10.dp, top = 20.dp, end = 10.dp, bottom = 10.dp).weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)){
                BackdropBlurScreen(rate = course.rate,date = course.startDate.toRussianFormat(), hasLike = true, handlerEvent,coverImages[randomIndex])
                Text( course.title, style = MaterialTheme.typography.titleMedium,fontWeight = FontWeight.Bold)
                Text( course.text, style = MaterialTheme.typography.bodySmall,maxLines = 2,
                    overflow = TextOverflow.Clip,softWrap = true)
                Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(
                        course.price + " ₽",
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Box()
                    {
                        Row {
                            Text(
                                text = stringResource(id = R.string.detail),
                                color = colorResource(R.color.green),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = 0.5.sp
                                ),
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "",
                                modifier = Modifier.size(16.dp),
                                tint = colorResource(R.color.green)
                            )
                        }
                    }
                }
            }
        }
    }
}