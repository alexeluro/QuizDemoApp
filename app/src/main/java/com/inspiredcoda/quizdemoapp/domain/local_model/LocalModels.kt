package com.inspiredcoda.quizdemoapp.domain.local_model

import com.inspiredcoda.quizdemoapp.domain.remote_model.Difficulty
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class QuizQuestion(
    @Json(name = "id") var id: String,
    @Json(name = "question") val question: String,
    @Json(name = "options") val options: List<String>,
    @Json(name = "answer") var answer: String,
    @Json(name = "difficulty") val difficulty: Difficulty
){

    fun toQuizAnswer(): QuizAnswer {
        return QuizAnswer(
            questionId = id,
            answer = answer
        )
    }

}

@JsonClass(generateAdapter = true)
data class QuizAnswer(
    @Json(name = "question_id") val questionId: String,
    @Json(name = "question_answer") val answer: String,
)

@JsonClass(generateAdapter = true)
data class Instructor(
    @Json(name = "id") val id: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "phone") val phone: String,
    @Json(name = "email") val email: String,
    @Json(name = "rating") val rating: Long,
    @Json(name = "description") val description: String
)

