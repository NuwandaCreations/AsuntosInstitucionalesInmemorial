package com.example.asuntosinstitucionalesinmemorial.data

import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.MaterialDao
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.RegalosDao
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.MaterialEntity
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.RegalosEntity
import com.example.asuntosinstitucionalesinmemorial.data.network.drive.DriveService
import com.example.asuntosinstitucionalesinmemorial.data.network.response.toDomain
import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.example.asuntosinstitucionalesinmemorial.domain.model.toData
import kotlinx.coroutines.flow.Flow

class     RepositoryImpl(
    val googleDriveService: DriveService,
    val regalosDao: RegalosDao,
    val materialDao: MaterialDao
) : Repository {
    override suspend fun downloadStorage(): ProtocolStorage {
        val response = googleDriveService.downloadDriveFile()
        return response?.toDomain() ?: ProtocolStorage()
    }

    override suspend fun addRegalosDB(vararg regalos: Regalos) {
        regalosDao.addRegalos(*regalos.map { it.toData() }.toTypedArray())
    }

    override suspend fun getRegalosDB(): Flow<List<RegalosEntity>> {
        return regalosDao.getAllRegalos()
    }

    override suspend fun deleteRegalosDB(vararg regalos: Regalos) {
        regalosDao.deleteRegalos(*regalos.map { it.toData() }.toTypedArray())
    }

    override suspend fun addMaterialDB(vararg material: Material) {
        materialDao.addMaterial(*material.map { it.toData() }.toTypedArray())
    }

    override suspend fun getMaterialDB(): Flow<List<MaterialEntity>> {
        return materialDao.getAllMaterial()
    }

    override suspend fun deleteMaterialDB(vararg material: Material) {
        materialDao.deleteMaterial(*material.map { it.toData() }.toTypedArray())
    }
}