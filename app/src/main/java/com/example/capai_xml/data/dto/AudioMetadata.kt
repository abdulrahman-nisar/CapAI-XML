package com.example.capai_xml.data.dto

data class AudioMetadata(
    val audio_duration: Double,
    val extension: String,
    val filename: String,
    val id: String,
    val number_of_channels: Int,
    val size: Int,
    val source: String
)