package com.example.capai_xml.domain.model

data class VideoResult(
    var transcriptionText: String? = null,
    var isTranscribing: Boolean = false,
    var isSuccess: Boolean = false,
    var errorMessage: String? = null
)
