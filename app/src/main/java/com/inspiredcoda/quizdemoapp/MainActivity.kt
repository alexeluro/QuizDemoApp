package com.inspiredcoda.quizdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.inspiredcoda.quizdemoapp.presentation.navigation.SetupNavGraph
import com.inspiredcoda.quizdemoapp.presentation.screens.home.QuizViewModel
import com.inspiredcoda.quizdemoapp.presentation.screens.instructor.InstructorViewModel
import com.inspiredcoda.quizdemoapp.presentation.theme.QuizDemoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizDemoAppTheme {

                val quizViewModel: QuizViewModel = viewModel()
                val instructorViewModel: InstructorViewModel = viewModel()
                navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.surface,
                ) {
                    Timber.d("Surface")
                    SetupNavGraph(
                        navController = navController,
                        quizViewModel = quizViewModel,
                        instructorViewModel = instructorViewModel
                    )
//                    HomeScreen(quizViewModel, navController)
                }


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val quizViewModel: QuizViewModel = viewModel()
    val instructorViewModel: InstructorViewModel = viewModel()
    val navController = rememberNavController()

    QuizDemoAppTheme {
        SetupNavGraph(
            navController = navController,
            quizViewModel = quizViewModel,
            instructorViewModel = instructorViewModel
        )
    }
}