package com.inspiredcoda.quizdemoapp.presentation.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspiredcoda.quizdemoapp.domain.DispatcherProvider
import com.inspiredcoda.quizdemoapp.domain.local_model.QuizQuestion
import com.inspiredcoda.quizdemoapp.domain.remote_model.QuizAnswerResponse
import com.inspiredcoda.quizdemoapp.domain.repository.quiz_repository.QuizRepository
import com.inspiredcoda.quizdemoapp.domain.repository.quiz_repository.QuizRepository.QuizResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val dispatcherProvider: DispatcherProvider,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _currentQuestion: MutableStateFlow<QuizQuestion?> = MutableStateFlow(null)
    val currentQuestion = _currentQuestion.asStateFlow()

    private val _isLastQuestion: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLastQuestion = _isLastQuestion.asStateFlow()

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage = _errorMessage.asStateFlow()

    private var allQuestions: MutableList<QuizQuestion> = mutableListOf()
    private var count = 0;

    private val _isDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDialogShown = _isDialogShown.asStateFlow()

    private val _score: MutableStateFlow<QuizAnswerResponse> =
        MutableStateFlow(QuizAnswerResponse("", "", ""))
    val score = _score.asStateFlow()


    init {
        getQuestions()
    }

    fun getQuestions() {
        viewModelScope.launch(dispatcherProvider.io()) {
            _isLoading.emit(true)
            when (val questions = quizRepository.getQuestions()) {
                is QuizResponse.GetQuestions -> {
                    count = 0
                    allQuestions.clear()
                    allQuestions.addAll(questions.value)

                    Timber.d("Total number of questions: ${allQuestions.size}")

                    _currentQuestion.emit(questions.value[count])
                }

                is QuizResponse.Failure -> {
                    _errorMessage.emit(questions.error)
                }

                else -> {}
            }

            _isLastQuestion.emit(false)
            _isLoading.emit(false)
        }
    }

    fun showDialog(showDialog: Boolean) {
        viewModelScope.launch {
            _isDialogShown.emit(showDialog)
        }
    }

    fun setSelectedAnswer(answer: String) {
        viewModelScope.launch {
            allQuestions[count].answer = answer
        }
    }

    fun submitAnswers() {
        viewModelScope.launch {
            _isLoading.emit(true)
            val answers = allQuestions.map {
                it.toQuizAnswer()
            }
            Timber.d("Answers submitted!")
            when (val scoreResponse = quizRepository.submitAnswers(answers)) {
                is QuizResponse.QuizScore -> {
                    showDialog(true)
                    _score.emit(scoreResponse.scoreResponse)
                    Timber.d("Score: ${scoreResponse.scoreResponse}")
                }

                is QuizResponse.Failure -> {
                    _errorMessage.emit(scoreResponse.error)
                }

                else -> {

                }
            }

            _isLoading.emit(false)
        }
    }

    fun nextQuestion() {
        viewModelScope.launch {
            Timber.d("Next Question")
            _isLoading.emit(true)

//            if (count < allQuestions.size) {
//                count++
//                _currentQuestion.emit(allQuestions[count])
//                Timber.d("Next Question: $count")
//            }
            count += 1
            _currentQuestion.emit(allQuestions[count])
            Timber.d("Next Question: $count")
            val isThisTheLastQuestion = count == (allQuestions.size - 1)
            Timber.d("LastQuestion? $isThisTheLastQuestion")
            _isLastQuestion.emit(isThisTheLastQuestion)
            _isLoading.emit(false)
        }
    }

    fun submitQuestion() {
        viewModelScope.launch {
            _isLoading.emit(true)
            count = 0

            _isLoading.emit(false)
        }
    }


}