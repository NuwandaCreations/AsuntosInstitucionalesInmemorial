package com.example.asuntosinstitucionalesinmemorial.domain.model

import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.MaterialEntity
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.EMPTY_NAME

data class Material(
    val cantidad: Int? = null,
    val localizacion: String? = null,
    val objeto: String? = null,
    val observaciones: String? = null
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
