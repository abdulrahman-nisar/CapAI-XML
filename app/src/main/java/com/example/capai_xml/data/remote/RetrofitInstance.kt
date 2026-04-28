package com.example.capai_xml.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

   private  fun getInstance(): Retrofit{
       return Retrofit.Builder()
           .baseUrl("https://api.gladia.io/v2/")
           .addConverterFactory(GsonConverterFactory.create())
           .build()

   }

    val api : GladiaApiService by lazy{
        getInstance().create(GladiaApiService::class.java)
    }
}