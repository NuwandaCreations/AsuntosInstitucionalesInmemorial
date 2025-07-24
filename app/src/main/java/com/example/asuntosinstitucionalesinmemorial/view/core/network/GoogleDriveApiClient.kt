package com.example.asuntosinstitucionalesinmemorial.view.core.network

import com.example.asuntosinstitucionalesinmemorial.view.core.network.response.ProtocolStorageResponse
import retrofit2.Response
import retrofit2.http.GET

interface GoogleDriveApiClient {
    @GET("file/d/1mo2eh-QtGGMikSWp9E9s0hhAUK5GD2KJ/view?usp=drive_link")
    suspend fun downloadFile(): Response<ProtocolStorageResponse>
}
