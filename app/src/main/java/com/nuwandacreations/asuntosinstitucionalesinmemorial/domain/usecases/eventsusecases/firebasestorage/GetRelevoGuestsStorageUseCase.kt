package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage

import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.events.InvitadosRelevoResponse
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GetRelevoGuestsStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(event: String): List<InvitadosRelevoResponse> {
        val jsonString = repository.getEventGuestsStorage(event).trimIndent()
        val listType = object : TypeToken<List<InvitadosRelevoResponse>>() {}.type
        return if (jsonString.isNotEmpty()) {
            Gson().fromJson(jsonString, listType)
        } else {
            emptyList()
        }
    }
}