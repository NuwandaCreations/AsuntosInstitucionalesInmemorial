package com.nuwandacreations.asuntosinstitucionalesinmemorial.data.database.storagedb.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Material
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EMPTY_NAME

@Entity
data class MaterialEntity(
    @PrimaryKey
    val objeto: String = EMPTY_NAME,
    val cantidad: Int?,
    val categoria: String?,
    val localizacion: String?,
    val observaciones: String?,
    val foto: String?
)

fun MaterialEntity.toDomain(): Material {
    return Material(
        categoria = categoria,
        cantidad = cantidad,
        localizacion = localizacion,
        objeto = objeto,
        observaciones = observaciones
    )
}