package com.nuwandacreations.asuntosinstitucionalesinmemorial.data

import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.database.storagedb.MaterialDao
import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.database.storagedb.RegalosDao
import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.database.storagedb.model.MaterialEntity
import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.database.storagedb.model.RegalosEntity
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.Repository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Material
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.toData
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    val regalosDao: RegalosDao,
    val materialDao: MaterialDao
) : Repository {
    override suspend fun addRegalosDB(vararg regalos: Regalos) {
        regalosDao.addRegalos(*regalos.map { it.toData() }.toTypedArray())
    }

    override suspend fun getRegalosDB(): Flow<List<RegalosEntity>> {
        return regalosDao.getAllRegalos()
    }

    override suspend fun deleteRegalosDB(vararg regalos: Regalos) {
        regalosDao.deleteRegalos(*regalos.map { it.toData() }.toTypedArray())
    }

    override suspend fun deleteAllRegalosDB() {
        regalosDao.deleteAllRegalos()
    }

    override suspend fun updateRegalosDB(vararg regalos: Regalos) {
        regalosDao.deleteAllRegalos()
        regalosDao.addRegalos(*regalos.map { it.toData() }.toTypedArray())
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

    override suspend fun deleteAllMaterialDB() {
        materialDao.deleteAllMaterial()
    }

    override suspend fun updateMaterialDB(vararg material: Material) {
        materialDao.deleteAllMaterial()
        materialDao.addMaterial(*material.map { it.toData() }.toTypedArray())
    }
}