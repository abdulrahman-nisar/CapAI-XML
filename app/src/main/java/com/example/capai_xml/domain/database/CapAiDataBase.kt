package com.example.capai_xml.domain.database

import com.example.capai_xml.domain.model.CaptionItem
import com.example.capai_xml.domain.model.TranscriptionItem
import com.example.capai_xml.domain.model.User

interface CapAiDataBase {

    fun addUser(user: User)
    fun getCurrentUser(): User?
    fun deleteCurrentUser() : Boolean
    fun addCaptionToHistory(captionItem: CaptionItem)
    fun deleteCaptionFromHistory(captionItem: CaptionItem) : Boolean
    fun addTranscriptionToHistory(transcriptionItem: TranscriptionItem)
    fun deleteTranscriptionFromHistory(transcriptionItem: TranscriptionItem) : Boolean
    fun getAllCaptionHistory() : List<CaptionItem>
    fun getAllTranscriptionHistory() : List<TranscriptionItem>
    fun clearCaptionHistory() : Boolean
}