package com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.EMPTY_NAME

@Entity
data class RegalosEntity(
    @PrimaryKey
    val objeto: String = EMPTY_NAME,
    val cantidad: Int?,
    val categoria: String?,
    val detalleLocalizacion: String?,
    val localizacion: String?,
    val requisitoRegalo: String?,
    val foto: String?
)

fun RegalosEntity.toDomain(): Regalos {
    return Regalos(
        cantidad,
        categoria,
        detalleLocalizacion,
        localizacion,
        objeto,
        requisitoRegalo
    )
}
