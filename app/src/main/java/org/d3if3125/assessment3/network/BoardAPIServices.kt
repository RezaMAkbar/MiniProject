package org.d3if3125.assessment3.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import org.d3if3125.assessment3.model.Board
import org.d3if3125.assessment3.model.Status
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://hippo-casual-nicely.ngrok-free.app/API/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BoardAPIServices {
    @GET("getBoard.php")
    suspend fun getBoard(
        @Header("Authorization") userId: String
    ): List<Board>

    @Multipart
    @POST("addBoard.php")
    suspend fun postBoard(
        @Header("Authorization") userId: String,
        @Part("boardName") boardName: RequestBody,
        @Part("createdBy") createdBy: RequestBody,
        @Part("text") text: RequestBody,
    ): Status

    @FormUrlEncoded
    @POST("deleteBoard.php")
    suspend fun deleteBoard(
        @Header("Authorization") userId: String,
        @Field("id") id: Int
    ): Status
}

object BoardApi {
    val service: BoardAPIServices by lazy {
        retrofit.create(BoardAPIServices::class.java)
    }

    fun getBoardUrl(imageUrl: String): String {
        return "${BASE_URL}getBoard.php?id=$imageUrl"
    }

    enum class ApiStatus{LOADING, SUCCESS, FAILED}
}