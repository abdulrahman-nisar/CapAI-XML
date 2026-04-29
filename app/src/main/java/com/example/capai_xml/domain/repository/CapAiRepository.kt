package com.example.capai_xml.domain.repository

import android.content.Context
import com.example.capai_xml.data.dto.GetTranscriptionResponse
import com.example.capai_xml.data.dto.TranscriptionInitiationResponse
import com.example.capai_xml.data.dto.UploadVideoResponse
import com.example.capai_xml.domain.model.CaptionItem
import com.example.capai_xml.domain.model.Length
import com.example.capai_xml.domain.model.User
import com.example.capai_xml.domain.model.TranscriptionItem
import okhttp3.MultipartBody

interface CapAiRepository {

    fun addUser(user: User)
    fun getCurrentUser(): User?
    fun deleteCurrentUser() : Boolean
    fun addCaptionToHistory(captionItem: CaptionItem)
    fun deleteCaptionFromHistory(captionItem: CaptionItem) : Boolean
    fun addTranscriptionToHistory(transcriptionItem: TranscriptionItem)
    fun deleteTranscriptionFromHistory(transcriptionItem: TranscriptionItem) : Boolean
    fun getAllCaptionHistory() : List<CaptionItem>
    fun getAllTranscriptionHistory() : List<TranscriptionItem>
    fun clearCaptionHistory()
    fun signUpWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun signInWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun signOut()
    suspend fun generateCaptionForImage(imageUri: String, length: Length, context: Context, onSuccess: (CaptionItem) -> Unit, onFailure: (Exception) -> Unit)
    suspend fun uploadVideo( apiKey : String , audio : MultipartBody.Part): UploadVideoResponse
    suspend fun initiateTranscription(apiKey : String , body : HashMap<String, Any>): TranscriptionInitiationResponse
    suspend fun getTranscriptionResult(apiKey:String,id:String): GetTranscriptionResponse

}