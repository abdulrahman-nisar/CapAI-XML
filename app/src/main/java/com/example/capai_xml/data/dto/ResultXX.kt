package com.example.capai_xml.data.dto

data class ResultXX(
    val channel: Int,
    val confidence: Int,
    val end: Int,
    val language: String,
    val speaker: Int,
    val start: Int,
    val text: String,
    val words: List<Word>
)