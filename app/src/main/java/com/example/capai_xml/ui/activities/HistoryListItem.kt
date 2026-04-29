package com.example.capai_xml.ui.activities

import com.example.capai_xml.domain.model.SourceTable

data class HistoryListItem(
    val id: Int,
    val name : String,
    val description : String,
    val source: SourceTable,
    val imageUri: String,
    val videoUri: String,
    val instagramCaption: String?,
    val facebookCaption: String?,
    val threadCaption: String?,
    val twitterCaption: String?,
    val pinterestCaption: String?,
    val linkedinCaption: String?,
    val snapChatCaption: String?,
    val tiktokCaption: String?,
    val transcriptionText: String?
)