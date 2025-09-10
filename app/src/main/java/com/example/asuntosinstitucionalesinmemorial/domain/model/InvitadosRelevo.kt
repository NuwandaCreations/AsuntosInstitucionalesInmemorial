package com.example.asuntosinstitucionalesinmemorial.domain.model

data class InvitadosRelevo(
    val empleo: String = "",
    val tratamiento: String = "",
    val nombre: String = "",
    val vehiculo: String = "",
    val observaciones: String = "",
    val visita1: String = "",
    val visita2: String = "",
    val concierto: String = "",
    val patio: String = "",
    val jardines: String = "",
    val vino: String = "",
    val notaProtocolo: String = "",
    var accedido: Boolean = false
)
