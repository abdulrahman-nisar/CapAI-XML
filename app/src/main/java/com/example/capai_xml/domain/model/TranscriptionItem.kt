package com.example.capai_xml.domain.model

data class TranscriptionItem(
    var id: Int = 0,
    var transcriptionText: String? = "",
    var videoUri : String,
    var source : SourceTable
)
