package com.example.asuntosinstitucionalesinmemorial.data

import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.MaterialDao
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.RegalosDao
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.toDomain
import com.example.asuntosinstitucionalesinmemorial.data.network.drive.DriveService
import com.example.asuntosinstitucionalesinmemorial.data.network.response.toDomain
import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos

class RepositoryImpl(val googleDriveService: DriveService, val regalosDao: RegalosDao, val materialDao: MaterialDao) : Repository {
    override suspend fun downloadStorage(): ProtocolStorage {
        val response = googleDriveService.downloadDriveFile()
        return response?.toDomain() ?: ProtocolStorage()
    }

    override suspend fun getRegalosDB(): List<Regalos> {
        var regalosList = emptyList<Regalos>()
        regalosDao.getAllRegalos().collect{ regalos ->
            regalosList = regalos.map { it.toDomain() }
        }
        return regalosList
    }
}