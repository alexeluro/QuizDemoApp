package com.inspiredcoda.quizdemoapp.presentation.screens.instructor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.inspiredcoda.quizdemoapp.domain.local_model.Instructor
import com.inspiredcoda.quizdemoapp.presentation.components.QuizTopAppBar
import com.inspiredcoda.quizdemoapp.presentation.theme.Gold



@Composable
fun InstructorScreen(
    instructorViewModel: InstructorViewModel,
    navController: NavController
) {

    val instructor by instructorViewModel.instructorState.collectAsState()
    val isLoading by instructorViewModel.isLoading.collectAsState()
    val error by instructorViewModel.errorMessage.collectAsState()

    Scaffold(
        modifier = Modifier,
        topBar = {
            QuizTopAppBar(
                title = "Instructors",
                navIcon = Icons.Default.ArrowBack,
                navAction = { navController.popBackStack() }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.secondary
                )
            }
            LazyColumn(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(instructor) { instructor ->
                    InstructorListItem(instructor = instructor)
                }
            }
        }
    }

}

@Preview
@Composable
fun InstructorListItem(
    instructor: Instructor = defaultInstructor
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background, RoundedCornerShape(2.dp))
            .shadow(1.dp, shape = RoundedCornerShape(2.dp))
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = "${instructor.lastName} ${instructor.firstName}",
            style = TextStyle(
                color = MaterialTheme.colors.onBackground,
                fontSize = 16.sp
            )
        )
        Row(
            modifier = Modifier.padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (x in 1..instructor.rating.toInt()) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Gold)
            }
        }
    }
}

private val defaultInstructor = Instructor(
    "1",
    "Johnson",
    "Ahuwa",
    "08035032170",
    "alexeluro@gmail.com",
    3,
    "Good lad"
)

private val defaultInstructorList = listOf(
    Instructor(
        "1",
        "Johnson",
        "Ahuwa",
        "08035032170",
        "alexeluro@gmail.com",
        3,
        "Good lad"
    ),
    Instructor(
        "1",
        "Johnson",
        "Ahuwa",
        "08035032170",
        "alexeluro@gmail.com",
        3,
        "Good lad"
    ),
    Instructor(
        "1",
        "Johnson",
        "Ahuwa",
        "08035032170",
        "alexeluro@gmail.com",
        3,
        "Good lad"
    ),
    Instructor(
        "1",
        "Johnson",
        "Ahuwa",
        "08035032170",
        "alexeluro@gmail.com",
        3,
        "Good lad"
    )
)