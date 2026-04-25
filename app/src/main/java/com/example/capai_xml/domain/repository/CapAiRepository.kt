package com.example.capai_xml.domain.repository

import com.example.capai_xml.domain.model.User

interface CapAiRepository {

    fun addUser(user: User)
    fun getCurrentUser(): User?
    fun deleteCurrentUser() : Boolean
    fun signUpWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun signInWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
}