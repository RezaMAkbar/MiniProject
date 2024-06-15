package org.d3if3125.assessment3.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3125.assessment3.model.Board
import org.d3if3125.assessment3.model.Chat
import org.d3if3125.assessment3.model.Status
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val BASE_URL = "https://hippo-casual-nicely.ngrok-free.app/API/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ChatApiServices {
    @GET("getChat.php")
    suspend fun getChat(
        @Header("Authorization") userId: String,
    ): List<Chat>

    @Multipart
    @POST("addChat.php")
    suspend fun postChat(
        @Header("Authorization") userId: String,
        @Part("name") name: RequestBody,
        @Part("text") text: RequestBody,
        @Part("boardID") boardID: RequestBody
    ): Status
}

object ChatApi {
    val service: ChatApiServices by lazy {
        retrofit.create(ChatApiServices::class.java)
    }

    enum class ChatApiStatus{LOADING, SUCCESS, FAILED}
}