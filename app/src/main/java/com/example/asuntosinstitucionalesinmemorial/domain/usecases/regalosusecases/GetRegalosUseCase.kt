package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases

import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.RegalosEntity
import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import kotlinx.coroutines.flow.Flow

class GetRegalosUseCase(val repository: Repository) {
    suspend operator fun invoke(): Flow<List<RegalosEntity>> {
        return repository.getRegalosDB()
    }
}