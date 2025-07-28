package com.example.asuntosinstitucionalesinmemorial.domain.usecases

import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage

class DownloadStorageUseCase(val repository: Repository) {
    suspend operator fun invoke(): ProtocolStorage {
        return repository.downloadStorage()
    }
}