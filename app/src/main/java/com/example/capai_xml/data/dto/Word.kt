package com.example.capai_xml.data.dto

data class Word(
    val confidence: Int,
    val end: Int,
    val start: Int,
    val word: String
)