package com.example.capai_xml.data.repository

import android.content.Context
import com.example.capai_xml.data.local.database.CapAiDataBaseImpl
import com.example.capai_xml.data.remote.AuthService
import com.example.capai_xml.domain.database.CapAiDataBase
import com.example.capai_xml.domain.model.User
import com.example.capai_xml.domain.repository.CapAiRepository

class CapAiRepositoryImpl(
    context : Context,
    private val capAiDataBase: CapAiDataBase = CapAiDataBaseImpl(context, "cap_ai_db", null,1),
    private val authService: AuthService = AuthService()
) : CapAiRepository{
    override fun addUser(user: User) {
       capAiDataBase.addUser(user)
    }

    override fun getCurrentUser(): User? {
        return capAiDataBase.getCurrentUser()
    }

    override fun deleteCurrentUser(): Boolean{
        return capAiDataBase.deleteCurrentUser()
    }

    override fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        authService.signUpWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    override fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        authService.signInWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    override fun signInWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        authService.signInWithGoogle(idToken, onSuccess, onFailure)
    }
}