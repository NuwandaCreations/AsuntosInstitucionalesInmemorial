package com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MaterialEntity (
    @PrimaryKey
    val objeto: String = "SIN NOMBRE",
    val cantidad: Int?,
    val localizacion: String?,
    val observaciones: String?,
    val foto: String?
)