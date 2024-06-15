package org.d3if3125.assessment3.view

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if3125.assessment3.model.Board
import org.d3if3125.assessment3.model.Chat
import org.d3if3125.assessment3.network.BoardApi
import org.d3if3125.assessment3.network.ChatApi

import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {
    var data = mutableStateOf(emptyList<Board>())
        private set

    var chatData = mutableStateOf(emptyList<Chat>())
        private set


    var status = MutableStateFlow(BoardApi.ApiStatus.LOADING)
        private set

    var statusChat = MutableStateFlow(ChatApi.ChatApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(userId: String) {
        viewModelScope.launch ( Dispatchers.IO ) {
            status.value = BoardApi.ApiStatus.LOADING
            try {
                data.value = BoardApi.service.getBoard(userId)
                status.value = BoardApi.ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = BoardApi.ApiStatus.FAILED
            }
        }
    }

    fun retrieveChatData(userId: String) {
        viewModelScope.launch ( Dispatchers.IO ) {
            statusChat.value = ChatApi.ChatApiStatus.LOADING
            try {
                chatData.value = ChatApi.service.getChat(userId)
                statusChat.value = ChatApi.ChatApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                statusChat.value = ChatApi.ChatApiStatus.FAILED
            }
        }
    }

    fun saveData(userId: String, boardName: String, createdBy: String, text: String) {
        viewModelScope.launch ( Dispatchers.IO ) {
            try {
               val result = BoardApi.service.postBoard(
                   userId,
                   boardName.toRequestBody("text/plain".toMediaTypeOrNull()),
                   createdBy.toRequestBody("text/plain".toMediaTypeOrNull()),
                   text.toRequestBody("text/plain".toMediaTypeOrNull())
               )

                if (result.status == "success")
                    retrieveData(userId)
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }
    //boardId: Int

    fun saveChatData(userId: String, name: String, text: String, boardId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = ChatApi.service.postChat(
                    userId = userId,
                    name = name.toRequestBody("text/plain".toMediaTypeOrNull()),
                    text = text.toRequestBody("text/plain".toMediaTypeOrNull()),
                    boardID = boardId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                )

                if (result.status == "success") {
                    retrieveData(userId)
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun deleteBoardData(userId: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = BoardApi.service.deleteBoard(userId, id)
                if (result.status == "success") {
                    retrieveData(userId)
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
    }

    fun clearMessage() { errorMessage.value = null }

}