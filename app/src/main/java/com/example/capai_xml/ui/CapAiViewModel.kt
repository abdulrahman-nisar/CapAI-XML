package com.example.capai_xml.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capai_xml.BuildConfig
import com.example.capai_xml.domain.model.Length
import com.example.capai_xml.domain.model.ImageResult
import com.example.capai_xml.domain.model.User
import com.example.capai_xml.domain.model.VideoResult
import com.example.capai_xml.domain.repository.CapAiRepository
import com.example.capai_xml.domain.usecase.GladiaTranscriptionServiceUseCase
import kotlinx.coroutines.launch

class CapAiViewModel(
    private val repository: CapAiRepository,
    private val gladiaTranscriptionServiceUseCase: GladiaTranscriptionServiceUseCase = GladiaTranscriptionServiceUseCase(repository)
) : ViewModel() {

    // Explicit overload to avoid NoSuchMethodError when the factory calls a one-arg ctor.
    constructor(repository: CapAiRepository) : this(
        repository,
        GladiaTranscriptionServiceUseCase(repository)
    )

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _videoResult = MutableLiveData<VideoResult?>(VideoResult())
    val videoResult: LiveData<VideoResult?> = _videoResult

    private val _imageResult = MutableLiveData<ImageResult?>(ImageResult())
    val imageResult: LiveData<ImageResult?> = _imageResult

    init {
        loadCurrentUser()
    }

    fun addUser(name: String, email: String) {
        repository.addUser(User(0, name, email, false))
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        _currentUser.value = repository.getCurrentUser()
    }

    fun deleteCurrentUser() {
        if (repository.deleteCurrentUser()) {
            _currentUser.value = null
        }
    }

    fun signUpWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        repository.signUpWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    fun signInWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        repository.signInWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        repository.signInWithGoogle(idToken, onSuccess, onFailure)
    }

    fun signOut() {
        repository.signOut()
        deleteCurrentUser()
    }

    fun generateCaptionForImage(imageUri: String, length: Length , context: Context) {
        _imageResult.value = (_imageResult.value ?: ImageResult()).copy(
            isGenerating = true,
            errorMessage = null,
            isSuccess = false
        )
        viewModelScope.launch {
            repository.generateCaptionForImage(imageUri, length, context, { caption ->
                _imageResult.postValue(
                    _imageResult.value?.copy(
                        isGenerating = false,
                        isSuccess = true,
                        errorMessage = null,
                        captionItem = caption
                    )
                )
            }, { exception ->
                _imageResult.postValue(
                    _imageResult.value?.copy(
                        isGenerating = false,
                        isSuccess = false,
                        errorMessage = exception.message ?: "Unknown error"
                    )
                )
            })
        }
    }

    fun transcribeFromVideoUrl(videoUrl: String,context: Context) {
        _videoResult.value = (_videoResult.value ?: VideoResult()).copy(
            isTranscribing = true,
            errorMessage = null,
            isSuccess = false
        )
        viewModelScope.launch {
            try {
                val transcriptionResponse =
                    gladiaTranscriptionServiceUseCase.transcribeVideo(
                        context,
                        videoUrl,
                        BuildConfig.GLADIA_API_KEY
                    )
                _videoResult.postValue(
                    _videoResult.value?.copy(
                        isTranscribing = false,
                        isSuccess = true,
                        errorMessage = null,
                        transcriptionText = transcriptionResponse.result.transcription.full_transcript
                    )
                )
            } catch (e: Exception) {
                _videoResult.postValue(
                    _videoResult.value?.copy(
                        isTranscribing = false,
                        isSuccess = false,
                        errorMessage = e.message ?: "Unknown error"
                    )
                )
            }

        }
    }

}