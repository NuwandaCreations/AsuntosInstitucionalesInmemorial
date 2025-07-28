package com.example.asuntosinstitucionalesinmemorial.data

import com.example.asuntosinstitucionalesinmemorial.data.network.drive.DriveService
import com.example.asuntosinstitucionalesinmemorial.data.network.response.toDomain
import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage

class RepositoryImpl(val googleDriveService: DriveService) : Repository {
    override suspend fun downloadStorage(): ProtocolStorage {
        val response = googleDriveService.downloadDriveFile()
        return response?.toDomain() ?: ProtocolStorage()
    }
}