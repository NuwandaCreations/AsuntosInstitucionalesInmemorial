package com.example.asuntosinstitucionalesinmemorial.domain

import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.MaterialEntity
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.RegalosEntity
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun downloadStorage(): ProtocolStorage
    suspend fun addRegalosDB(vararg regalos: Regalos)
    suspend fun getRegalosDB(): Flow<List<RegalosEntity>>
    suspend fun deleteRegalosDB(vararg regalos: Regalos)
    suspend fun addMaterialDB(vararg material: Material)
    suspend fun getMaterialDB(): Flow<List<MaterialEntity>>
    suspend fun deleteMaterialDB(vararg material: Material)
}