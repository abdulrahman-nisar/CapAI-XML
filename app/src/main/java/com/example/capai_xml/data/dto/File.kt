package com.example.capai_xml.data.dto

data class File(
    val audio_duration: Double,
    val filename: String,
    val id: String,
    val number_of_channels: Int,
    val source: String
)