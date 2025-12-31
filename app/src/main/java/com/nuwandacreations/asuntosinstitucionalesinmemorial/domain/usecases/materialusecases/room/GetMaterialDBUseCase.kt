package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room

import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.database.storagedb.model.MaterialEntity
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.Repository
import kotlinx.coroutines.flow.Flow

class GetMaterialDBUseCase(val repository: Repository) {
    suspend operator fun invoke(): Flow<List<MaterialEntity>> {
        return repository.getMaterialDB()
    }
}