package com.example.capai_xml.data.remote

import com.example.capai_xml.data.dto.GetTranscriptionResponse
import com.example.capai_xml.data.dto.TranscriptionInitiationResponse
import com.example.capai_xml.data.dto.UploadVideoResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface GladiaApiService {

    @Multipart
    @POST("upload")
    suspend fun uploadVideo(
        @Header("x-gladia-key") apiKey: String,
        @Part audio : MultipartBody.Part
    ): UploadVideoResponse

    @POST("pre-recorded")
    suspend fun initiateTranscription(
        @Header("x-gladia-key") apiKey: String,
        @Body body : Map<String, Any>
    ) : TranscriptionInitiationResponse

    @GET("pre-recorded/{id}")
    suspend fun getTranscriptionResult(
        @Header("x-gladia-key") apiKey: String,
        @Path("id") id: String
    ) : GetTranscriptionResponse
}