package com.inspiredcoda.quizdemoapp.domain.repository.instructor_repository

import com.inspiredcoda.quizdemoapp.domain.ApiService
import com.inspiredcoda.quizdemoapp.domain.DispatcherProvider
import com.inspiredcoda.quizdemoapp.domain.remote_model.RemoteInstructor
import com.inspiredcoda.quizdemoapp.domain.repository.BaseRepository
import com.inspiredcoda.quizdemoapp.domain.repository.instructor_repository.InstructorRepository.InstructorResponse
import com.squareup.moshi.Moshi
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class InstructorRepositoryImpl @Inject constructor(
    private val moshi: Moshi,
    private val apiService: ApiService,
    private val dispatcherProvider: DispatcherProvider
) : InstructorRepository, BaseRepository() {

    override suspend fun getInstructors(): InstructorResponse {
        return try {
            val remoteInstructors =
                request { apiService.getInstructors() }.responseBody ?: emptyList()
            val instructors = remoteInstructors.map { value ->
                Timber.d("Instructor: ${value}: ${moshi.adapter(RemoteInstructor::class.java)}\n")
                value.toInstructor()
            }
            InstructorResponse.GetInstructor(instructors)
        } catch (ex: IOException) {
            InstructorResponse.Failure(ex.message ?: "An error occurred!")
        } catch (ex: Exception) {
            InstructorResponse.Failure(ex.message ?: "An error occurred!")
        }
    }


}