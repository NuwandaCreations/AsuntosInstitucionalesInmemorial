package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GetRegalosStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(): List<Regalos> {
        val jsonString = repository.getRegalosStorageJSON().trimIndent()
        val listType = object : TypeToken<List<Regalos>>() {}.type
        return if (jsonString.isNotEmpty()) {
            Gson().fromJson(jsonString, listType)
        } else {
            emptyList()
        }
    }
}