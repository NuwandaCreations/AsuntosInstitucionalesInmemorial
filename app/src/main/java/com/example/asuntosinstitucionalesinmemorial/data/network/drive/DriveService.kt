package com.example.asuntosinstitucionalesinmemorial.data.network.drive

import com.example.asuntosinstitucionalesinmemorial.data.network.response.ProtocolStorageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class DriveService(private val retrofit: Retrofit) {
    suspend fun downloadDriveFile(): ProtocolStorageResponse? {
        return withContext(Dispatchers.IO) {
            retrofit.create(DriveApiClient::class.java).downloadFile().body()
        }
    }
}