package com.example.capai_xml.data.dto

data class StructuredDataExtraction(
    val error: Error,
    val exec_time: Int,
    val is_empty: Boolean,
    val results: String,
    val success: Boolean
)