package com.inspiredcoda.quizdemoapp.domain.repository.quiz_repository

import com.inspiredcoda.quizdemoapp.domain.ApiService
import com.inspiredcoda.quizdemoapp.domain.DispatcherProvider
import com.inspiredcoda.quizdemoapp.domain.local_model.QuizAnswer
import com.inspiredcoda.quizdemoapp.domain.remote_model.RemoteQuizQuestion
import com.inspiredcoda.quizdemoapp.domain.repository.BaseRepository
import com.inspiredcoda.quizdemoapp.domain.repository.quiz_repository.QuizRepository.QuizResponse
import com.squareup.moshi.Moshi
import okio.IOException
import timber.log.Timber
import javax.inject.Inject

class QuizQuestionImpl @Inject constructor(
    private val moshi: Moshi,
    private val apiService: ApiService,
    private val dispatcherProvider: DispatcherProvider
) : QuizRepository, BaseRepository() {

    override suspend fun getQuestions(): QuizResponse {
        return try {
            val remoteQuestions = request { apiService.getQuestions() }.responseBody ?: emptyList()
            val questions = remoteQuestions.map { value ->
                Timber.d(
                    "Quiz question: ${
                        moshi.adapter(RemoteQuizQuestion::class.java).toJson(value)
                    }\n"
                )
                value.toQuizQuestion()
            }
            QuizResponse.GetQuestions(questions)
        } catch (ex: IOException) {
            QuizResponse.Failure(ex.message ?: "An error occurred!")
        } catch (ex: Exception) {
            QuizResponse.Failure(ex.message ?: "An error occurred!")
        }
    }

    override suspend fun submitAnswers(answers: List<QuizAnswer>): QuizResponse {
        return try {
            val scoreResponse = request { apiService.computeScores(answers) }

            if (scoreResponse.status && scoreResponse.responseBody != null) {
                QuizResponse.QuizScore(scoreResponse.responseBody)
            } else {
                QuizResponse.Failure(scoreResponse.responseMessage)

            }

        } catch (ex: Exception) {
            QuizResponse.Failure(ex.message ?: "Couldn't compute scores")
        }
    }

    override suspend fun addQuestion(question: RemoteQuizQuestion): QuizResponse {
        return QuizResponse.AddQuestion
    }

    override suspend fun deleteQuestion(questionId: Long): QuizResponse {
        return QuizResponse.DeleteQuestion
    }

}