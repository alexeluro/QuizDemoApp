package com.inspiredcoda.quizdemoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.inspiredcoda.quizdemoapp.presentation.NavRoute
import com.inspiredcoda.quizdemoapp.presentation.screens.home.HomeScreen
import com.inspiredcoda.quizdemoapp.presentation.screens.home.QuizViewModel
import com.inspiredcoda.quizdemoapp.presentation.screens.instructor.InstructorScreen
import com.inspiredcoda.quizdemoapp.presentation.screens.instructor.InstructorViewModel
import com.inspiredcoda.quizdemoapp.presentation.screens.quiz_screen.QuizScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    quizViewModel: QuizViewModel,
    instructorViewModel: InstructorViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.HomeScreen.route
    ) {
        composable(route = NavRoute.HomeScreen.route) {
            HomeScreen(
                quizViewModel = quizViewModel,
                navController = navController
            )
        }
        composable(route = NavRoute.TakeQuizScreen.route) {
            QuizScreen(
                quizViewModel = quizViewModel,
                navController = navController
            )
        }
        composable(route = NavRoute.InstructorScreen.route) {
            InstructorScreen(
                instructorViewModel = instructorViewModel,
                navController = navController
            )
        }
    }
}