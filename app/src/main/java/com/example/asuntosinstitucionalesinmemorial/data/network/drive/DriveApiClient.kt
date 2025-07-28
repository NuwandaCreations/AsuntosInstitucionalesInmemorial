package com.example.asuntosinstitucionalesinmemorial.data.network.drive

import com.example.asuntosinstitucionalesinmemorial.data.network.response.ProtocolStorageResponse
import com.example.asuntosinstitucionalesinmemorial.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface DriveApiClient {
    @GET(Constants.Companion.JSON_BIN)
    suspend fun downloadFile(): Response<ProtocolStorageResponse>
}