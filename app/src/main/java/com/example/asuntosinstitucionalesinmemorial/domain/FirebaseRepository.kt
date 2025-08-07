package com.example.asuntosinstitucionalesinmemorial.domain

import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun setStorageFirestore(storage: ProtocolStorage)
    fun setRegaloFirestore(regalo: Regalos)
    fun setMaterialFirestore(material: Material)
    fun getRegalosFirestore(): Flow<List<Regalos>>
    fun getMaterialFirestore(): Flow<List<Material>>
    fun deleteRegalosFirestore(regalos: Regalos)
    fun deleteMaterialFirestore(material: Material)
}