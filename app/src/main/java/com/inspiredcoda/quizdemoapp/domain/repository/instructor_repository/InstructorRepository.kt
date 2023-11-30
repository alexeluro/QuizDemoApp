package com.inspiredcoda.quizdemoapp.domain.repository.instructor_repository

import com.inspiredcoda.quizdemoapp.domain.local_model.Instructor


interface InstructorRepository {

    suspend fun getInstructors(): InstructorResponse

    sealed interface InstructorResponse {
        data class GetInstructor(val value: List<Instructor>) : InstructorResponse
        data class Failure(val message: String) : InstructorResponse
    }

}