package com.example.asuntosinstitucionalesinmemorial.domain

import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage

interface Repository {
    suspend fun downloadStorage(): ProtocolStorage
}