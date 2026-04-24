package com.example.capai_xml.data.repository

import com.example.capai_xml.domain.database.CapAiDataBase
import com.example.capai_xml.domain.repository.CapAiRepository

class CapAiRepositoryImpl(
    private val capAiDataBase: CapAiDataBase
) : CapAiRepository{
}