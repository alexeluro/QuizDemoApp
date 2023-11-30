package com.inspiredcoda.quizdemoapp.presentation

sealed class NavRoute(val route: String) {
    object HomeScreen: NavRoute("home_screen")
    object AddQuestionScreen: NavRoute("add_question_screen")
    object TakeQuizScreen: NavRoute("take_quiz_screen")
    object InstructorScreen: NavRoute("instructors_screen")
}