package com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases

import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material

class AddMaterialUseCase(val repository: Repository) {
    suspend operator fun invoke(vararg material: Material) {
        repository.addMaterialDB(*material)
    }
}