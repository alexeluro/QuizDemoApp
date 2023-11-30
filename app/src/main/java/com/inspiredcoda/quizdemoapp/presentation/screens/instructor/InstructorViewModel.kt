package com.inspiredcoda.quizdemoapp.presentation.screens.instructor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspiredcoda.quizdemoapp.domain.DispatcherProvider
import com.inspiredcoda.quizdemoapp.domain.local_model.Instructor
import com.inspiredcoda.quizdemoapp.domain.remote_model.RemoteQuizQuestion
import com.inspiredcoda.quizdemoapp.domain.repository.instructor_repository.InstructorRepository
import com.inspiredcoda.quizdemoapp.domain.repository.instructor_repository.InstructorRepository.InstructorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructorViewModel @Inject constructor(
    private val instructorRepository: InstructorRepository,
    private val dispatcherProvider: DispatcherProvider,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _instructor: MutableStateFlow<List<Instructor>> = MutableStateFlow(listOf())
    val instructorState = _instructor.asStateFlow()

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage = _errorMessage.asStateFlow()

    private var allQuestions: MutableList<RemoteQuizQuestion> = mutableListOf()
    private var count = 0;

    init {
        getInstructors()
    }

    private fun getInstructors() {
        viewModelScope.launch {
            _isLoading.emit(true)
            when (val response = instructorRepository.getInstructors()) {
                is InstructorResponse.GetInstructor -> {
                    _instructor.emit(response.value)
                }

                is InstructorResponse.Failure -> {
                    _errorMessage.emit(response.message)
                }
            }
            _isLoading.emit(false)
        }
    }


}