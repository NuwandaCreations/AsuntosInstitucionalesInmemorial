package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model

data class Invitados(
    val grupo: String = "",
    val nombre: String = "",
    val cargo: String = "",
    val vehiculo: String = "",
    var accedido: Boolean = false,
    val foto: String = "",
    val color: String = ""
)
