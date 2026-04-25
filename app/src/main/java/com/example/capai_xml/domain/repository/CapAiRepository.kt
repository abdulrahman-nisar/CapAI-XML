package com.example.capai_xml.domain.repository

import android.content.Context
import com.example.capai_xml.domain.model.CaptionItem
import com.example.capai_xml.domain.model.Length
import com.example.capai_xml.domain.model.User

interface CapAiRepository {

    fun addUser(user: User)
    fun getCurrentUser(): User?
    fun deleteCurrentUser() : Boolean
    fun addCaptionToHistory(captionItem: CaptionItem)
    fun clearCaptionHistory()
    fun signUpWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun signInWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    suspend fun generateCaptionForImage(imageUri: String, length: Length, context: Context, onSuccess: (CaptionItem) -> Unit, onFailure: (Exception) -> Unit)

}