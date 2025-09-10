package com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room

import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material

class DeleteMaterialDBUseCase(val repository: Repository) {
    suspend operator fun invoke(vararg material: Material) {
        repository.deleteMaterialDB(*material)
    }
}