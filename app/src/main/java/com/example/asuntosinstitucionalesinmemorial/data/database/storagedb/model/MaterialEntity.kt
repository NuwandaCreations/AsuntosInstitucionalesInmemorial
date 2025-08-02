package com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.EMPTY_NAME

@Entity
data class MaterialEntity(
    @PrimaryKey
    val objeto: String = EMPTY_NAME,
    val cantidad: Int?,
    val localizacion: String?,
    val observaciones: String?,
    val foto: String?
)

fun MaterialEntity.toDomain(): Material {
    return Material(
        cantidad,
        localizacion,
        objeto,
        observaciones
    )
}