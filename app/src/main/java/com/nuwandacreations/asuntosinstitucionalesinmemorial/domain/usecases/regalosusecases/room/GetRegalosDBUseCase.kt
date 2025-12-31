package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room

import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.database.storagedb.model.RegalosEntity
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.Repository
import kotlinx.coroutines.flow.Flow

class GetRegalosDBUseCase(val repository: Repository) {
    suspend operator fun invoke(): Flow<List<RegalosEntity>> {
        return repository.getRegalosDB()
    }
}