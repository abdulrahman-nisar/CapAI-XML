package com.example.capai_xml.data.dto

data class ResultXX(
    val channel: Int,
    val confidence: Double,
    val end: Double,
    val language: String,
    val speaker: Int,
    val start: Double,
    val text: String,
)