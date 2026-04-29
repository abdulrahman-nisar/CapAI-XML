package com.example.capai_xml.data.dto

data class Translation(
    val error: Error,
    val exec_time: Double,
    val is_empty: Boolean,
    val results: List<ResultXXXX>,
    val success: Boolean
)