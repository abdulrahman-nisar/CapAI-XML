package com.example.capai_xml.data.dto

data class TranslationConfig(
    val context: String,
    val context_adaptation: Boolean,
    val informal: Boolean,
    val lipsync: Boolean,
    val match_original_utterances: Boolean,
    val model: String,
    val target_languages: List<String>
)