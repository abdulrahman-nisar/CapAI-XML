package com.example.capai_xml.data.dto

data class Error(
    val exception: String,
    val message: String,
    val status_code: Int
)