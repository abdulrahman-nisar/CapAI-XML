package com.example.capai_xml

import android.app.Application
import com.example.capai_xml.data.repository.CapAiRepositoryImpl
import com.example.capai_xml.domain.repository.CapAiRepository

class CapAiApp : Application() {

    val repository: CapAiRepository by lazy {
        CapAiRepositoryImpl(applicationContext)
    }
}

