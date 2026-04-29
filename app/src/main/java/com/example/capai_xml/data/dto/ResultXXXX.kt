package com.example.capai_xml.data.dto

data class ResultXXXX(
    val error: Error,
    val full_transcript: String,
    val languages: List<String>,
    val sentences: List<Sentence>,
    val subtitles: List<Subtitle>,
)