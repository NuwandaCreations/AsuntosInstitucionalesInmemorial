package com.nuwandacreations.asuntosinstitucionalesinmemorial.util

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun dateToTimestamp (fecha: String?): Timestamp? {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = sdf.parse(fecha ?: "")
    return date?.let { Timestamp(it) }
}

fun hexToColorInt(hex: String): Int? {
    val isHex = (hex.startsWith("#") && hex.length == 7)
    return if (isHex) {
        val cleanHex = hex.removePrefix("#")
        val argb = if (cleanHex.length == 6) {
            "FF$cleanHex"
        } else {
            cleanHex
        }
        argb.toLong(16).toInt()
    } else {
        null
    }
}