package com.inspiredcoda.quizdemoapp.domain

import com.inspiredcoda.quizdemoapp.domain.local_model.QuizAnswer
import com.inspiredcoda.quizdemoapp.domain.remote_model.BaseResponse
import com.inspiredcoda.quizdemoapp.domain.remote_model.QuizAnswerResponse
import com.inspiredcoda.quizdemoapp.domain.remote_model.RemoteInstructor
import com.inspiredcoda.quizdemoapp.domain.remote_model.RemoteQuizQuestion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/questions")
    suspend fun getQuestions(): Response<BaseResponse<List<RemoteQuizQuestion>>>

    @GET("/instructors")
    suspend fun getInstructors(): Response<BaseResponse<List<RemoteInstructor>>>

    @POST("/compute/questions")
    suspend fun computeScores(@Body answers: List<QuizAnswer>): Response<BaseResponse<QuizAnswerResponse>>

}