package com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GetMaterialStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(): List<Material> {
        val jsonString = repository.getMaterialStorageJSON().trimIndent()
        val listType = object : TypeToken<List<Material>>() {}.type
        return if (jsonString.isNotEmpty()) {
            Gson().fromJson(jsonString, listType)
        } else {
            emptyList()
        }
    }
}