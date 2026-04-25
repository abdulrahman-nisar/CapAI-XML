package com.example.capai_xml.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capai_xml.domain.model.User
import com.example.capai_xml.domain.repository.CapAiRepository

class CapAiViewModel(
    private val repository: CapAiRepository
) : ViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

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

}