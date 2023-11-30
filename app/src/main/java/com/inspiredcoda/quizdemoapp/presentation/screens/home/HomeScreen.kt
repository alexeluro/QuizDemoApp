package com.inspiredcoda.quizdemoapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.inspiredcoda.quizdemoapp.presentation.NavRoute
import com.inspiredcoda.quizdemoapp.presentation.components.IconButton
import timber.log.Timber

@Composable
fun HomeScreen(
    quizViewModel: QuizViewModel,
    navController: NavController
) {

    val errors by quizViewModel.errorMessage.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val menuItems = listOf<DashboardMenu>(
        DashboardMenu(
            "Add Question", Icons.Filled.Add
        ) {
            quizViewModel.getQuestions()
        },
        DashboardMenu(
            "Take Quiz", Icons.Filled.Star
        ) {
            navController.navigate(NavRoute.TakeQuizScreen.route)
        },
        DashboardMenu(
            "View Instructors", Icons.Filled.Person
        ) {
            navController.navigate(NavRoute.InstructorScreen.route)
        },
    )

    Timber.d("HomeScreen")
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { HomeAppBar() },
        scaffoldState = scaffoldState,
        snackbarHost = {
            LaunchedEffect(key1 = it) {
                it.showSnackbar(it.currentSnackbarData?.message ?: "")
            }
        },
    ) { parentPaddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(parentPaddingValue)
                .background(color = Color.White)
        ) {

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                columns = GridCells.Fixed(2)
            ) {
                items(menuItems) {menuItem ->
                    IconButton(
                        modifier = Modifier
                            .padding(5.dp),
                        onClick = { menuItem.onClick.invoke() },
                        icon = menuItem.icon,
                        text = menuItem.title
                    )
                }

//                Spacer(modifier = Modifier.width(1.dp))
//                IconButton(
//                    modifier = Modifier
//                        .padding(5.dp)
//                        .weight(1f),
//                    onClick = { navController.navigate(NavRoute.TakeQuizScreen.route) },
//                    text = "Take Quiz",
//                    icon = Icons.Filled.Star
//                )
            }


        }
    }

}

data class DashboardMenu(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
)