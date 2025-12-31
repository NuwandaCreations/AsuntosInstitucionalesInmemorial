package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model

import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.database.storagedb.model.MaterialEntity
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EMPTY_NAME

data class Material(
    val categoria: String? = null,
    var cantidad: Int? = null,
    val localizacion: String? = null,
    val objeto: String? = null,
    val observaciones: String? = null,
    val foto: String = ""
)

fun Material.toData(): MaterialEntity {
    return MaterialEntity(
        objeto = objeto ?: EMPTY_NAME,
        cantidad = cantidad,
        categoria = categoria,
        localizacion = localizacion,
        observaciones = observaciones,
        foto = null
    )
}
