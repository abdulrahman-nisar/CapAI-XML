package com.example.capai_xml.data.remote

import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import com.example.capai_xml.BuildConfig
import com.example.capai_xml.domain.model.CaptionItem
import com.example.capai_xml.domain.model.Length
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import org.json.JSONObject

class GeminiCaptionGeneration {

    suspend fun generateCaptionForImage(
        imageUri: String,
        length: Length,
        context : Context,
        onSuccess: (CaptionItem) -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        val generativeModel = GenerativeModel(
            modelName = "gemini-3-flash-preview",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
        val inputStream = context.contentResolver.openInputStream(imageUri.toUri())
        val bitmap = BitmapFactory.decodeStream(inputStream)

        if (bitmap == null) {
            onFailure(IllegalArgumentException("Unable to decode image from: $imageUri"))
            return
        }

        try{
            val prompt = buildString {
                append(
                    "I want to generate captions for these platforms: " +
                        "instagram, facebook, thread, twitter, pinterest, linkedin, snapchat, tiktok. "
                )
                append("And this is the length: ${length.name}.\n\n")
                append(
                    "Return ONLY valid JSON with exactly these keys: " +
                        "instagram, facebook, thread, twitter, pinterest, linkedin, snapchat, tiktok. " +
                        "Each value must be a string caption. No markdown."
                )
            }

            val inputContent = content {
                image(bitmap)
                text(prompt)
            }

            val response = generativeModel.generateContent(inputContent)
            val raw = response.text?.trim().orEmpty()

            if (raw.isBlank()) {
                onFailure(IllegalStateException("Empty response from Gemini"))
                return
            }

            val captions = parseCaptions(raw)
            if(captions.isEmpty()) {
                onFailure(IllegalStateException("Failed to parse captions from Gemini response"))
                return
            }

            val item = CaptionItem(
                id = 0,
                instagramCaption = captions["instagram"],
                facebookCaption = captions["facebook"],
                twitterCaption = captions["twitter"],
                pinterestCaption = captions["pinterest"],
                linkedinCaption = captions["linkedin"],
                threadCaption = captions["thread"],
                snapChatCaption = captions["snapchat"],
                tiktokCaption = captions["tiktok"],
                imageUri = imageUri
            )

            onSuccess(item)

        }catch (e: Exception){
            onFailure(e)
        }

    }

    private fun parseCaptions(raw: String): Map<String, String> {

        val trimmed = raw.trim()
        if (trimmed.startsWith("{") && trimmed.endsWith("}")) {
            val json = JSONObject(trimmed)
            return mapOf(
                "instagram" to json.optString("instagram").trim(),
                "facebook" to json.optString("facebook").trim(),
                "thread" to json.optString("thread").trim(),
                "twitter" to json.optString("twitter").trim(),
                "pinterest" to json.optString("pinterest").trim(),
                "linkedin" to json.optString("linkedin").trim(),
                "snapchat" to json.optString("snapchat").trim(),
                "tiktok" to json.optString("tiktok").trim(),
            ).filterValues { it.isNotBlank() }
        }else{
            return emptyMap()
        }

    }

}