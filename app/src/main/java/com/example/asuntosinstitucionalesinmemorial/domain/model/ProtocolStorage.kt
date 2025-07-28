package com.example.asuntosinstitucionalesinmemorial.domain.model

data class ProtocolStorage(
    val material: List<Material> = emptyList(),
    val regalos: List<Regalos> = emptyList()
)
