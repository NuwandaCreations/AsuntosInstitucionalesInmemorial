package com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RegalosEntity(
    @PrimaryKey
    val objeto: String = "SIN NOMBRE",
    val cantidad: Int?,
    val categoria: String?,
    val detalleLocalizacion: String?,
    val localizacion: String?,
    val requisitoRegalo: String?,
    val foto: String?
)
