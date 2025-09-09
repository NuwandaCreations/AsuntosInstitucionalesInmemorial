package com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases

import com.example.asuntosinstitucionalesinmemorial.data.network.response.InvitadosResponse
import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Invitados
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GetEventGuestsStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(event: String): List<InvitadosResponse> {
        val jsonString = repository.getEventGuestsStorage(event).trimIndent()
        val listType = object : TypeToken<List<InvitadosResponse>>() {}.type
        return if (jsonString.isNotEmpty()) {
            Gson().fromJson(jsonString, listType)
        } else {
            emptyList()
        }
    }
}