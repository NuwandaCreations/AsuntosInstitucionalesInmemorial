package com.example.asuntosinstitucionalesinmemorial.domain.model

import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.MaterialEntity
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.EMPTY_NAME

data class Material(
    val categoria: String? = null,
    val cantidad: Int? = null,
    val localizacion: String? = null,
    val objeto: String? = null,
    val requisitoRegalo: String? = null
)
//TODO NO ES REQUISITO, SERÍA OBSERVACIONES, importante que el json sean números la cantidad, ahora error por ser string
fun Material.toData(): MaterialEntity {
    return MaterialEntity(
        objeto ?: EMPTY_NAME,
        cantidad,
        localizacion,
        requisitoRegalo,
        null
    )
}
