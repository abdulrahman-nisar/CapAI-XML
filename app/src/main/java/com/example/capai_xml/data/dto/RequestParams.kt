package com.example.capai_xml.data.dto

data class RequestParams(
    val audio_to_llm: Boolean,
    val audio_to_llm_config: AudioToLlmConfig,
    val audio_url: String,
    val callback: Boolean,
    val callback_config: CallbackConfig,
    val callback_url: String,
    val custom_spelling: Boolean,
    val custom_spelling_config: CustomSpellingConfig,
    val custom_vocabulary: Boolean,
    val custom_vocabulary_config: CustomVocabularyConfig,
    val diarization: Boolean,
    val diarization_config: DiarizationConfig,
    val language_config: LanguageConfig,
    val named_entity_recognition: Boolean,
    val pii_redaction: Boolean,
    val pii_redaction_config: PiiRedactionConfig,
    val punctuation_enhanced: Boolean,
    val sentences: Boolean,
    val sentiment_analysis: Boolean,
    val subtitles: Boolean,
    val subtitles_config: SubtitlesConfig,
    val summarization: Boolean,
    val summarization_config: SummarizationConfig,
    val translation: Boolean,
    val translation_config: TranslationConfig
)