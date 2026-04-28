package com.example.capai_xml.data.dto

data class PiiRedactionConfig(
    val entity_types: List<String>,
    val processed_text_type: String
)