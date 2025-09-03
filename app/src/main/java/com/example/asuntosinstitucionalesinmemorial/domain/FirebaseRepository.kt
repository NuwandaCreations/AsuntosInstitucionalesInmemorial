package com.example.asuntosinstitucionalesinmemorial.domain

import com.example.asuntosinstitucionalesinmemorial.data.network.response.EventsResponse
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.google.firebase.storage.ListResult
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    suspend fun getRegalosStorageJSON(): String
    suspend fun getMaterialStorageJSON(): String
    suspend fun getPhotosStorage(objeto: String): String
    suspend fun getAllPhotosStorage(): ListResult
    suspend fun getEventGuestsStorage(event: String): String
    suspend fun setRegaloFirestore(regalo: Regalos)
    suspend fun setMaterialFirestore(material: Material)
    fun getRegalosFirestore(): Flow<List<Regalos>>
    fun getMaterialFirestore(): Flow<List<Material>>
    fun getEventsFirestore(): Flow<List<EventsResponse>>
    fun getRegaloByIdFirestore(regalo: String): Flow<Regalos>
    fun getMaterialByIdFirestore(material: String): Flow<Material>
    fun getEventByIdFirestore(event: String): Flow<EventsResponse>
    suspend fun deleteRegalosFirestore(regalos: Regalos)
    suspend fun deleteMaterialFirestore(material: Material)
    suspend fun updateRegalosFirestore(regalosList: List<Regalos>)
    suspend fun updateMaterialFirestore(materialList: List<Material>)
}