package com.inspiredcoda.quizdemoapp.domain.remote_model

import com.inspiredcoda.quizdemoapp.domain.local_model.Instructor
import com.inspiredcoda.quizdemoapp.domain.local_model.QuizQuestion
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteQuizQuestion(
    @Json(name = "id") var id: String?,
    @Json(name = "question") val question: String?,
    @Json(name = "options") val options: List<String>?,
    @Json(name = "answer") val answer: String?,
    @Json(name = "difficulty") val difficulty: Difficulty?
) {

    fun toQuizQuestion(): QuizQuestion {
        return QuizQuestion(
            id = id!!,
            question = question ?: "",
            options = options ?: listOf(),
            answer = answer ?: "",
            difficulty = difficulty ?: Difficulty.BEGINNER
        )
    }

}

@JsonClass(generateAdapter = true)
data class QuizAnswerResponse(
    @Json(name = "score") val score: String,
    @Json(name = "total") val total: String,
    @Json(name = "comment") val comment: String
)

@JsonClass(generateAdapter = true)
data class RemoteInstructor(
    @Json(name = "id") val id: String?,
    @Json(name = "first_name") val firstName: String?,
    @Json(name = "last_name") val lastName: String?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "rating") val rating: Long?,
    @Json(name = "description") val description: String?
) {

    fun toInstructor(): Instructor {
        return Instructor(
            id = id!!,
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            phone = phone ?: "",
            email = email ?: "",
            rating = rating ?: 1,
            description = description ?: "",
        )
    }

}

enum class Difficulty {
    BEGINNER, INTERMEDIATE, ADVANCED
}