package com.example.capai_xml.data.dto

data class SubtitlesConfig(
    val formats: List<String>,
    val maximum_characters_per_row: Int,
    val maximum_duration: Double,
    val maximum_rows_per_caption: Int,
    val minimum_duration: Int,
    val style: String
)