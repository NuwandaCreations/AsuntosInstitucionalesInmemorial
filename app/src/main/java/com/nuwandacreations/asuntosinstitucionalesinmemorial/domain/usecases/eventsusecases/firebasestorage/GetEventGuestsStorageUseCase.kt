package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage

import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.events.InvitadosResponse
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
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