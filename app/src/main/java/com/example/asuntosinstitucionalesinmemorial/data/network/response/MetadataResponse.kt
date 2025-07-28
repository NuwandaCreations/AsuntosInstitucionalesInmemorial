package com.example.asuntosinstitucionalesinmemorial.data.network.response

data class MetadataResponse(
    val createdAt: String,
    val name: String,
    val readCountRemaining: Int,
    val timeToExpire: Int
)