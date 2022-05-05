package com.example.project1.network.api

import com.example.project1.data.models.DefaultMemeResponse
import com.example.project1.data.models.MemeResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MemesApi {

    @GET("get_memes")
    fun getMemesList(): Deferred<Response<DefaultMemeResponse>>

    @POST("caption_image")
    fun postMeme(
        @Query("template_id") template_id: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("text0") text0: String,
        @Query("text1") text1: String
    ): Deferred<Response<MemeResponse>>

}