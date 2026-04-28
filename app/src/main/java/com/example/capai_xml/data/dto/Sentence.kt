package com.example.capai_xml.data.dto

data class Sentence(
    val error: Error,
    val exec_time: Int,
    val is_empty: Boolean,
    val results: List<String>,
    val success: Boolean
)