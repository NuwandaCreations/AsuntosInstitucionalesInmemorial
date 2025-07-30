package com.example.asuntosinstitucionalesinmemorial.domain

import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos

interface Repository {
    suspend fun downloadStorage(): ProtocolStorage
    suspend fun getRegalosDB(): List<Regalos>
}