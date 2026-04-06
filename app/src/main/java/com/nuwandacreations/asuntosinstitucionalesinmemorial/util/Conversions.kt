package com.nuwandacreations.asuntosinstitucionalesinmemorial.util

import com.google.firebase.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

fun dateToTimestamp(fecha: String?): Timestamp? {
    return try {
        if (fecha.isNullOrEmpty()) return null else {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = sdf.parse(fecha)
            date?.let { Timestamp(it) }
        }
    } catch (_: ParseException) {
        null
    }
}

fun timestampToDate(timestamp: Timestamp?): String? {
    return try {
        if (timestamp != null) {
            val date = timestamp.toDate()
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.format(date)
        } else {
            null
        }
    } catch (_: ParseException) {
        null
    }
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

fun String.toCamelCase(): String =
    split(" ").joinToString("") { it.replaceFirstChar(Char::uppercaseChar) }