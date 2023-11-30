package com.inspiredcoda.quizdemoapp.domain.repository.quiz_repository

import com.inspiredcoda.quizdemoapp.domain.local_model.QuizAnswer
import com.inspiredcoda.quizdemoapp.domain.local_model.QuizQuestion
import com.inspiredcoda.quizdemoapp.domain.remote_model.QuizAnswerResponse
import com.inspiredcoda.quizdemoapp.domain.remote_model.RemoteQuizQuestion

interface QuizRepository {

    suspend fun getQuestions(): QuizResponse
    suspend fun addQuestion(question: RemoteQuizQuestion): QuizResponse
    suspend fun deleteQuestion(questionId: Long): QuizResponse
    suspend fun submitAnswers(answers: List<QuizAnswer>): QuizResponse


    sealed interface QuizResponse {
        data class GetQuestions(val value: List<QuizQuestion>) : QuizResponse
        object AddQuestion : QuizResponse
        object DeleteQuestion : QuizResponse
        data class QuizScore(val scoreResponse: QuizAnswerResponse) : QuizResponse
        data class Failure(val error: String) : QuizResponse
    }

}