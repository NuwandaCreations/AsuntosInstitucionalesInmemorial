package com.example.asuntosinstitucionalesinmemorial.domain.model

data class Evento(
    val id: String? = null,
    val nombre: String? = null,
    val descripcion: String? = null,
    val fecha: String? = null,
    val lugar: String? = null,
    val imagen: String? = null,
    val esRelevoGuardia: Boolean? = null
)
