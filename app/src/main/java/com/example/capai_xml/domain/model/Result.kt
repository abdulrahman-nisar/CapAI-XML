package com.example.capai_xml.domain.model

data class Result(
    var captionItem: CaptionItem? = null,
    var isGenerating: Boolean = false,
    var isSuccess: Boolean = false,
    var errorMessage: String? = null
)
