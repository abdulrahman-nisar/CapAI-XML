package com.example.capai_xml.domain.database

import com.example.capai_xml.domain.model.CaptionItem
import com.example.capai_xml.domain.model.User

interface CapAiDataBase {

    fun addUser(user: User)
    fun getCurrentUser(): User?
    fun deleteCurrentUser() : Boolean
    fun addCaptionToHistory(captionItem: CaptionItem)
    fun clearCaptionHistory()
}