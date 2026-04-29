package com.example.capai_xml.domain.usecase

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.delay
import com.example.capai_xml.domain.repository.CapAiRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import androidx.core.net.toUri
import com.example.capai_xml.data.dto.GetTranscriptionResponse

class GladiaTranscriptionServiceUseCase(
    private val repository : CapAiRepository
){

    suspend fun transcribeVideo(
        context: Context,
        videoUriString: String,
        apiKey: String,
        pollDelayMs: Long = 2_000L,
        maxAttempts: Int = 60
    ): GetTranscriptionResponse {
        val videoUri = videoUriString.toUri()

        val videoPart = createMultipartFromUri(context, videoUri)
        val uploadResponse = repository.uploadVideo(apiKey, videoPart)

        val initiationBody = buildInitiateTranscriptionBody(audioUrl = uploadResponse.audio_url)
        val initiationResponse = repository.initiateTranscription(apiKey, initiationBody)

        return pollTranscriptionResult(
            apiKey = apiKey,
            transcriptionId = initiationResponse.id,
            pollDelayMs = pollDelayMs,
            maxAttempts = maxAttempts
        )
    }

    private fun buildInitiateTranscriptionBody(audioUrl: String): HashMap<String, Any> {
        return hashMapOf(
            "audio_url" to audioUrl,
            "subtitles" to true,
            "subtitles_config" to hashMapOf(
                "formats" to listOf("srt")
            )
        )
    }

    private suspend fun pollTranscriptionResult(
        apiKey: String,
        transcriptionId: String,
        pollDelayMs: Long,
        maxAttempts: Int
    ): GetTranscriptionResponse {
        require(maxAttempts > 0) { "maxAttempts must be > 0" }
        require(pollDelayMs >= 0) { "pollDelayMs must be >= 0" }

        var lastResponse: GetTranscriptionResponse? = null
        repeat(maxAttempts) { attempt ->
            val response = repository.getTranscriptionResult(apiKey, transcriptionId)
            lastResponse = response

            when (response.status.lowercase()) {
                "done", "completed", "complete", "success", "succeeded", "finished" -> {
                    if (response.error_code != 0) {
                        throw IllegalStateException(
                            "Gladia transcription completed with error_code=${response.error_code}"
                        )
                    }
                    return response
                }
                "error", "failed", "failure" -> {
                    throw IllegalStateException(
                        "Gladia transcription failed (status=${response.status}, error_code=${response.error_code})"
                    )
                }
                else -> {
                    // keep polling
                }
            }

            if (attempt < maxAttempts - 1 && pollDelayMs > 0) delay(pollDelayMs)
        }

        throw IllegalStateException(
            "Gladia transcription timed out after $maxAttempts attempts. Last status=${lastResponse?.status}"
        )
    }

    private fun createMultipartFromUri(
        context: Context,
        uri: Uri,
        partName: String = "audio"
    ): MultipartBody.Part {
        val contentResolver = context.contentResolver

        val mimeType = contentResolver.getType(uri) ?: "application/octet-stream"

        val cacheFile = copyUriToCacheFile(context, uri)
        val requestBody = cacheFile.asRequestBody(mimeType.toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(partName, cacheFile.name, requestBody)
    }

    private fun copyUriToCacheFile(context: Context, uri: Uri): File {
        val target = File(context.cacheDir, "upload_${System.currentTimeMillis()}")

        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(target).use { output ->
                input.copyTo(output)
            }
        } ?: throw IllegalArgumentException("Unable to open URI stream: $uri")

        return target
    }
}