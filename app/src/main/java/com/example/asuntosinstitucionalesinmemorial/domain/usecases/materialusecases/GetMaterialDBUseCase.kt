package com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases

import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.MaterialEntity
import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import kotlinx.coroutines.flow.Flow

class GetMaterialDBUseCase(val repository: Repository) {
    suspend operator fun invoke(): Flow<List<MaterialEntity>> {
        return repository.getMaterialDB()
    }
}