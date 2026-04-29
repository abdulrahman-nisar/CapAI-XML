package com.example.capai_xml.data.dto

data class Result(
    val metadata: Metadata,
    val moderation: Moderation,
    val sentences: Sentences,
    val structured_data_extraction: StructuredDataExtraction,
    val transcription: Transcription,
    val translation: Translation
)