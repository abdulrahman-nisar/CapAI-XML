package com.example.capai_xml.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capai_xml.domain.model.Length
import com.example.capai_xml.domain.model.Result
import com.example.capai_xml.domain.model.User
import com.example.capai_xml.domain.repository.CapAiRepository
import kotlinx.coroutines.launch

class CapAiViewModel(
    private val repository: CapAiRepository
) : ViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _result = MutableLiveData<Result?>(Result())
    val result: LiveData<Result?> = _result

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

    fun generateCaptionForImage(imageUri: String, length: Length , context: Context) {
        _result.value = (_result.value ?: Result()).copy(
            isGenerating = true,
            errorMessage = null,
            isSuccess = false
        )
        viewModelScope.launch {
            repository.generateCaptionForImage(imageUri, length, context, { caption ->
                _result.postValue(
                    _result.value?.copy(
                        isGenerating = false,
                        isSuccess = true,
                        errorMessage = null,
                        captionItem = caption
                    )
                )
            }, { exception ->
                _result.postValue(
                    _result.value?.copy(
                        isGenerating = false,
                        isSuccess = false,
                        errorMessage = exception.message ?: "Unknown error"
                    )
                )
            })
        }
    }

}