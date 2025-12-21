package com.example.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.Course
import com.example.main.components.Loading
import com.example.utils.toRussianFormat
import com.example.utils.ui.BackdropBlurScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen(viewModel: MainViewModel = hiltViewModel())
{
    val stateUI by viewModel.stateLoadCourse.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val favoritesUser by viewModel.favoritesUser.collectAsStateWithLifecycle()
    var isSort by rememberSaveable { mutableStateOf(false) }


    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) { data ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(data.visuals.message)
                }
            }
        },
       contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        when(stateUI.contentState){
            is LoadCourseState.Error -> {
                val msgErr = stringResource(id = R.string.error_load)
                LaunchedEffect(stateUI.contentState) {
                    scope.launch {
                        snackbarHostState.showSnackbar(msgErr)
                    }
                }
            }
            is LoadCourseState.Idle -> {}
            is LoadCourseState.Loading -> {
                Loading()
            }
            is LoadCourseState.Success -> {
                val courses = (stateUI.contentState as LoadCourseState.Success).courses
                courses.let{
                    LazyColumn(Modifier.padding(innerPadding)) {
                        item{
                            Row(Modifier.padding(8.dp).height(48.dp)){

                                Box(modifier = Modifier.padding(end = 8.dp).fillMaxSize().background(color = colorResource(R.color.dark_gray),
                                    shape =RoundedCornerShape(24.dp)).weight(1f),
                                    contentAlignment = Alignment.CenterStart) {
                                    Row(horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically){
                                        Box(Modifier.size(48.dp),contentAlignment = Alignment.Center){
                                            Icon(
                                                imageVector = Icons.Filled.Search,
                                                contentDescription = "",
                                                modifier = Modifier.size(24.dp),
                                                tint = Color.White
                                            )
                                        }
                                        Text(stringResource(R.string.search),style = MaterialTheme.typography.bodyMedium)

                                    }
                                }
                                Box(modifier = Modifier.width(48.dp).fillMaxHeight().background(color = colorResource(R.color.dark_gray),
                                    shape =RoundedCornerShape(24.dp)),
                                    contentAlignment = Alignment.Center){
                                    Icon(
                                        imageVector = Icons.Outlined.FilterAlt,
                                        contentDescription = "",
                                        modifier = Modifier.size(24.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }

                        item {
                            Row(
                                modifier = Modifier.padding(top= 8.dp, bottom = 8.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {

                                Box(Modifier.padding(end = 8.dp).clickable{ isSort = !isSort })
                                {
                                    Row(horizontalArrangement= Arrangement.spacedBy(4.dp)){
                                        Text(
                                            text = stringResource(id = R.string.sort),
                                            color = colorResource(R.color.green)
                                        )
                                        Icon(
                                            painter = painterResource(R.drawable.sort),
                                            contentDescription = "",
                                            modifier = Modifier.size(16.dp),
                                            tint = colorResource(R.color.green)
                                        )
                                    }
                                }
                            }
                        }
                        items(items = if (isSort) it.sortedByDescending { it1 -> it1.startDate } else it.sortedBy { it1 -> it1.startDate },
                            key = { course -> course.id } ) {
                            course ->
                            val isFavorite = favoritesUser.any { it.id == course.id }
                            val courseHasLike = if (isFavorite) course.copy(hasLike = true) else course.copy(hasLike = false)
                            val handlerEvent = if (isFavorite) {
                                {viewModel.handleEvent(MainUiEvent.OnRemoveFavoriteCourse(course))}
                            } else {
                                {viewModel.handleEvent(MainUiEvent.OnSaveFavoriteCourse(course))}
                            }
                            CardCourse(handlerEvent,courseHasLike)
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun CardCourse(handlerEvent: () -> Job, course: Course) {
    val coverImages = rememberSaveable {
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

                BackdropBlurScreen(rate = course.rate,date = course.startDate.toRussianFormat(), hasLike = course.hasLike, handlerEvent,coverImages[randomIndex])
                Text( course.title,  style = MaterialTheme.typography.titleMedium,fontWeight = FontWeight.Bold)
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