package com.inspiredcoda.quizdemoapp.presentation.screens.quiz_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.inspiredcoda.quizdemoapp.presentation.NavRoute
import com.inspiredcoda.quizdemoapp.presentation.components.QuizTopAppBar
import com.inspiredcoda.quizdemoapp.presentation.screens.home.QuizViewModel

@Composable
fun QuizScreen(
    quizViewModel: QuizViewModel,
    navController: NavHostController = rememberNavController(),
) {

    val question by quizViewModel.currentQuestion.collectAsState()
    val options = question?.options ?: listOf("Alex", "Mane", "Rodirguez", "Schultz")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options.get(0)) }

    val isLastQuestion by quizViewModel.isLastQuestion.collectAsState()

    val isDialogShown by quizViewModel.isDialogShown.collectAsState()
    val quizResponse by quizViewModel.score.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            QuizTopAppBar(
                title = "Questions",
                navIcon = Icons.Filled.ArrowBack,
                navAction = { navController.popBackStack() }
            )
        },
    ) { parentPaddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(parentPaddingValue.calculateBottomPadding() + 16.dp)
                .background(MaterialTheme.colors.background)
        ) {
            Column(modifier = Modifier) {

                Text(
                    text = question?.question ?: "What is your name?",
                    style = TextStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                options?.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1.merge(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .background(color = MaterialTheme.colors.primary)
                    .align(Alignment.End),
                onClick = {
                    quizViewModel.setSelectedAnswer(selectedOption)

                    if (!isLastQuestion) {
                        quizViewModel.nextQuestion()
                    } else {
                        quizViewModel.submitAnswers()
                    }
                }
            ) {
                Text(text = if (!isLastQuestion) "Next" else "Submit")
            }

            if (isDialogShown) {
                QuizResultDialog(
                    title = "Quiz Result",
                    message = quizResponse.comment,
                    score = quizResponse.score,
                    total = quizResponse.total
                ) {
                    navController.navigate(NavRoute.HomeScreen.route)
                    quizViewModel.showDialog(false)
                }
            }

        }
    }
}

@Preview
@Composable
fun QuizResultDialog(
    title: String = "Quiz Result",
    message: String = "Outstanding performance",
    score: String = "7",
    total: String = "10",
    action: (() -> Unit)? = null
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(10.dp),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = title,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = message
            )

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Bottom),
                    text = score,
                    style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.Bottom),
                    text = "/${total}"
                )
            }

            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .background(color = Color.Transparent)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .clickable(role = Role.Button) { action?.invoke() },
                text = "Home",
                style = TextStyle(
                    color = MaterialTheme.colors.primary
                )
            )
        }
    }
}