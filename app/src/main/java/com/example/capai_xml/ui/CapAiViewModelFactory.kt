package com.example.capai_xml.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capai_xml.domain.repository.CapAiRepository

class CapAiViewModelFactory(
    private val repository: CapAiRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CapAiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CapAiViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

