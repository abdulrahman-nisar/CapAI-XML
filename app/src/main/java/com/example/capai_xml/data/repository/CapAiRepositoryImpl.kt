package com.example.capai_xml.data.repository

import android.content.Context
import com.example.capai_xml.data.dto.GetTranscriptionResponse
import com.example.capai_xml.data.dto.TranscriptionInitiationResponse
import com.example.capai_xml.data.dto.UploadVideoResponse
import com.example.capai_xml.data.local.database.CapAiDataBaseImpl
import com.example.capai_xml.data.remote.AuthService
import com.example.capai_xml.data.remote.GeminiCaptionGeneration
import com.example.capai_xml.data.remote.RetrofitInstance
import com.example.capai_xml.domain.database.CapAiDataBase
import com.example.capai_xml.domain.model.CaptionItem
import com.example.capai_xml.domain.model.Length
import com.example.capai_xml.domain.model.TranscriptionItem
import com.example.capai_xml.domain.model.User
import com.example.capai_xml.domain.repository.CapAiRepository
import com.example.capai_xml.domain.usecase.GladiaTranscriptionServiceUseCase
import okhttp3.MultipartBody
import java.security.PrivilegedAction

class CapAiRepositoryImpl(
    context : Context? = null,
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

    override fun deleteCaptionFromHistory(captionItem: CaptionItem): Boolean {
        return capAiDataBase.deleteCaptionFromHistory(captionItem)
    }

    override fun addTranscriptionToHistory(transcriptionItem: TranscriptionItem) {
        capAiDataBase.addTranscriptionToHistory(transcriptionItem)
    }

    override fun deleteTranscriptionFromHistory(transcriptionItem: TranscriptionItem): Boolean {
        return capAiDataBase.deleteTranscriptionFromHistory(transcriptionItem)
    }

    override fun getAllCaptionHistory(): List<CaptionItem> {
        return capAiDataBase.getAllCaptionHistory()
    }

    override fun getAllTranscriptionHistory(): List<TranscriptionItem> {
        return capAiDataBase.getAllTranscriptionHistory()
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

    override fun signOut() {
        authService.signOut()
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

    override suspend fun uploadVideo(apiKey: String, audio: MultipartBody.Part): UploadVideoResponse {
        return RetrofitInstance.api.uploadVideo(apiKey,audio)
    }

    override suspend fun initiateTranscription(
        apiKey: String,
        body: HashMap<String, Any>
    ) : TranscriptionInitiationResponse {
        return RetrofitInstance.api.initiateTranscription(apiKey,body)
    }

    override suspend fun getTranscriptionResult(apiKey: String, id: String): GetTranscriptionResponse {
        return RetrofitInstance.api.getTranscriptionResult(apiKey,id)
    }
}