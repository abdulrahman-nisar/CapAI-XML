package com.example.capai_xml.data.dto

data class GetTranscriptionResponse(
    val completed_at: String,
    val created_at: String,
    val error_code: Int,
    val `file`: File,
    val id: String,
    val kind: String,
    val request_id: String,
    val result: Result,
    val status: String,
    val version: Int
)