package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.tasks.await

class GetAllPhotosStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(): MutableMap<String, String> {
        val urlMap: MutableMap<String, String> = mutableMapOf()
        val result = repository.getAllPhotosStorage()
        result.items.forEach { item ->
            val name = item.name
            val url = item.downloadUrl.await().toString()
            urlMap[name.removeSuffix(".jpg")] = url
        }
        Tasks.whenAll()
        return urlMap
    }
}