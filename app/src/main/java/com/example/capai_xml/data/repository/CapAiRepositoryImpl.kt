package com.example.capai_xml.data.repository

import android.content.Context
import com.example.capai_xml.data.local.database.CapAiDataBaseImpl
import com.example.capai_xml.data.remote.AuthService
import com.example.capai_xml.data.remote.GeminiCaptionGeneration
import com.example.capai_xml.domain.database.CapAiDataBase
import com.example.capai_xml.domain.model.CaptionItem
import com.example.capai_xml.domain.model.Length
import com.example.capai_xml.domain.model.User
import com.example.capai_xml.domain.repository.CapAiRepository
import java.security.PrivilegedAction

class CapAiRepositoryImpl(
    context : Context,
    private val capAiDataBase: CapAiDataBase = CapAiDataBaseImpl(context, "cap_ai_db", null,1),
    private val authService: AuthService = AuthService(),
    private val geminiCaptionGenerator : GeminiCaptionGeneration = GeminiCaptionGeneration()
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

    override fun addCaptionToHistory(captionItem: CaptionItem) {
        capAiDataBase.addCaptionToHistory(captionItem)
    }

    override fun clearCaptionHistory() {
        capAiDataBase.clearCaptionHistory()
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

    override suspend fun generateCaptionForImage(
        imageUri: String,
        length: Length,
        context: Context,
        onSuccess: (CaptionItem) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        geminiCaptionGenerator.generateCaptionForImage(imageUri, length, context,onSuccess, onFailure)
    }
}