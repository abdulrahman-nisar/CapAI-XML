package com.example.capai_xml.data.dto

data class AudioToLlmConfig(
    val model: String,
    val prompts: List<String>
)