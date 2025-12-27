package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model

data class ProtocolStorage(
    val material: List<Material> = emptyList(),
    val regalos: List<Regalos> = emptyList()
)
