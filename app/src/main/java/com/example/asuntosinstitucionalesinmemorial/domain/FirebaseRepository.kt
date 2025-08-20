package com.example.asuntosinstitucionalesinmemorial.domain

import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    suspend fun getRegalosStorageJSON(): String
    suspend fun getMaterialStorageJSON(): String
    suspend fun setRegaloFirestore(regalo: Regalos)
    suspend fun setMaterialFirestore(material: Material)
    fun getRegalosFirestore(): Flow<List<Regalos>>
    fun getMaterialFirestore(): Flow<List<Material>>
    suspend fun deleteRegalosFirestore(regalos: Regalos)
    suspend fun deleteMaterialFirestore(material: Material)
    suspend fun deleteAllRegalosFirestore()
    suspend fun deleteAllMaterialFirestore()
}