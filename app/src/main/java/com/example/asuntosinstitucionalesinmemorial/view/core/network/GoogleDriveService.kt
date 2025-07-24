package com.example.asuntosinstitucionalesinmemorial.view.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class GoogleDriveService(private val retrofit: Retrofit) {
    suspend fun downloadDriveFile() {
        withContext(Dispatchers.IO) {
            val response = retrofit.create(GoogleDriveApiClient::class.java).downloadFile()
            val myRespuesta = response.body()?.regalos ?: ""
            val myRespuesta2 = response.body()?.material ?: ""
        }
    }
}