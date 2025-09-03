package com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GetEventGuestsStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(event: String): String {
//        suspend operator fun invoke(event: String): List<Invitado> {

//        val jsonString = repository.getRegalosStorageJSON().trimIndent()
//        val listType = object : TypeToken<List<Regalos>>() {}.type
//        return if (jsonString.isNotEmpty()) {
//            Gson().fromJson(jsonString, listType)
//        } else {
//            emptyList()
//        }
        return repository.getEventGuestsStorage(event)
    }
}