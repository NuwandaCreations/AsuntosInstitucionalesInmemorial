package com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.storage

data class MetadataResponse(
    val createdAt: String,
    val name: String,
    val readCountRemaining: Int,
    val timeToExpire: Int
)