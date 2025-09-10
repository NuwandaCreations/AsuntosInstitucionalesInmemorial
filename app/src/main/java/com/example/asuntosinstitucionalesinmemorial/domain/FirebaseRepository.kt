package com.example.asuntosinstitucionalesinmemorial.domain

import com.example.asuntosinstitucionalesinmemorial.data.network.response.events.EventsResponse
import com.example.asuntosinstitucionalesinmemorial.domain.model.Invitados
import com.example.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo
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
    suspend fun setGuestsFirestore(evento: String, invitado: Invitados)
    suspend fun setRelevoGuestsFirestore(evento: String, invitado: InvitadosRelevo)
    fun getRegalosFirestore(): Flow<List<Regalos>>
    fun getMaterialFirestore(): Flow<List<Material>>
    fun getEventsFirestore(): Flow<List<EventsResponse>>
    fun getGuestsFirestore(evento: String): Flow<List<Invitados>>
    fun getRelevoGuestsFirestore(evento: String): Flow<List<InvitadosRelevo>>
    fun getRegaloByIdFirestore(regalo: String): Flow<Regalos>
    fun getMaterialByIdFirestore(material: String): Flow<Material>
    fun getEventByIdFirestore(event: String): Flow<EventsResponse>
    suspend fun deleteRegalosFirestore(regalos: Regalos)
    suspend fun deleteMaterialFirestore(material: Material)
    suspend fun updateRegalosFirestore(regalosList: List<Regalos>)
    suspend fun updateMaterialFirestore(materialList: List<Material>)
}