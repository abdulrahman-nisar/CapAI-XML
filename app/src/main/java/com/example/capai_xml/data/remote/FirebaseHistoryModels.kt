package com.example.capai_xml.data.remote

import com.example.capai_xml.domain.model.CaptionItem
import com.example.capai_xml.domain.model.SourceTable
import com.example.capai_xml.domain.model.TranscriptionItem

// Firebase requires a no-arg constructor and nullable fields for deserialization.
data class FirebaseCaptionItem(
    val instagramCaption: String? = null,
    val facebookCaption: String? = null,
    val threadCaption: String? = null,
    val twitterCaption: String? = null,
    val pinterestCaption: String? = null,
    val linkedinCaption: String? = null,
    val snapChatCaption: String? = null,
    val tiktokCaption: String? = null,
    val imageUri: String? = null,
    val createdAt: Long? = null
) {
    fun toDomain(): CaptionItem = CaptionItem(
        id = 0,
        instagramCaption = instagramCaption,
        facebookCaption = facebookCaption,
        threadCaption = threadCaption,
        twitterCaption = twitterCaption,
        pinterestCaption = pinterestCaption,
        linkedinCaption = linkedinCaption,
        snapChatCaption = snapChatCaption,
        tiktokCaption = tiktokCaption,
        imageUri = imageUri.orEmpty(),
        source = SourceTable.CAPTION
    )

    companion object {
        fun fromDomain(item: CaptionItem): FirebaseCaptionItem = FirebaseCaptionItem(
            instagramCaption = item.instagramCaption,
            facebookCaption = item.facebookCaption,
            threadCaption = item.threadCaption,
            twitterCaption = item.twitterCaption,
            pinterestCaption = item.pinterestCaption,
            linkedinCaption = item.linkedinCaption,
            snapChatCaption = item.snapChatCaption,
            tiktokCaption = item.tiktokCaption,
            imageUri = item.imageUri,
            createdAt = System.currentTimeMillis()
        )
    }
}

data class FirebaseTranscriptionItem(
    val transcriptionText: String? = null,
    val videoUri: String? = null,
    val createdAt: Long? = null
) {
    fun toDomain(): TranscriptionItem = TranscriptionItem(
        id = 0,
        transcriptionText = transcriptionText,
        videoUri = videoUri.orEmpty(),
        source = SourceTable.TRANSCRIPTION
    )

    companion object {
        fun fromDomain(item: TranscriptionItem): FirebaseTranscriptionItem = FirebaseTranscriptionItem(
            transcriptionText = item.transcriptionText,
            videoUri = item.videoUri,
            createdAt = System.currentTimeMillis()
        )
    }
}

data class FirebaseHistoryBundle(
    val captions: List<FirebaseCaptionItem> = emptyList(),
    val transcriptions: List<FirebaseTranscriptionItem> = emptyList()
)

