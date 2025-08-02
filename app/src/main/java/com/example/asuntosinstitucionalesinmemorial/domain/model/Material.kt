package com.example.asuntosinstitucionalesinmemorial.domain.model

import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.MaterialEntity
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.EMPTY_NAME

data class Material(
    val cantidad: Int?,
    val localizacion: String?,
    val objeto: String?,
    val observaciones: String?
)

fun Material.toData(): MaterialEntity {
    return MaterialEntity(
        objeto ?: EMPTY_NAME,
        cantidad,
        localizacion,
        observaciones,
        null
    )
}
