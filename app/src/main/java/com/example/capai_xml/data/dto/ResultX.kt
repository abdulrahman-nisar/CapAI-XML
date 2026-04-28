package com.example.capai_xml.data.dto

data class ResultX(
    val error: Error,
    val exec_time: Int,
    val is_empty: Boolean,
    val results: Results,
    val success: Boolean
)