package com.nuwandacreations.asuntosinstitucionalesinmemorial.util

class Constants {
    companion object {
        const val BASE_URL = "https://api.jsonbin.io/"
        const val EMPTY_NAME = "SIN NOMBRE"
        const val REGALOS = "regalos"
        const val MATERIAL = "material"
        const val EVENTOS = "eventos"
        const val RELEVO_ID = "RelevoDeLaGuardia"
        const val STORAGE = "storage"
        const val GUESTS = "invitados"
        const val REGALOS_JSON = "0-otros/regalos.json"
        const val MATERIAL_JSON = "0-otros/material.json"
        const val GUESTS_JSON = "1-eventos/"
        const val EVENTS_PHOTOS_JPG = "1-eventos/imagenes/"
        const val GUESTS_PHOTOS_JPG = "1-eventos/imagenes/invitados/"
        const val GUESTS_TICKETS = "1-eventos/entradas/"
        const val GUESTS_BASE_TICKETS = "1-eventos/entradas/tickets-base/"
        const val CONFIRM = "Confirmar"
        const val CANCEL = "Cancelar"
        const val AFIRMATIVE = "Sí"
        const val NEGATIVE = "No"

        //ERROR
        const val ERROR_UPDATING_EVENT = "Error actualizando evento"
        const val ERROR_GETTING_GUESTS = "Error al obtener los invitados del evento"
        const val ERROR_LOADING_GUEST = "Error al cargar invitado"
        const val ERROR_SETTING_GUEST = "Error al actualizar invitado"
        const val ERROR_SETTING_ACCESS = "Error al cambiar el acceso del invitado"
        const val ERROR_UNBINDING_CAMERA = "Error al liberar cámara"
        const val ERROR_STARTING_CAMERA = "Error al iniciar cámara"
        const val ERROR_VALIDATING_GUEST = "Invitación no localizada en la base de datos"
        const val ERROR_SCANNING_QR = "Error al escanear"
        const val ERROR_GENERATING_QR = "Error al generar lo códigos QR"
        const val ERROR_GENERATING_PDF = "Error al convertir a PDF"
        const val ERROR_COMPLETING_EVENT = "Complete los campos para crear el evento"
        const val ERROR_CREATING_EVENT = "No se ha podido crear el evento"
    }
}