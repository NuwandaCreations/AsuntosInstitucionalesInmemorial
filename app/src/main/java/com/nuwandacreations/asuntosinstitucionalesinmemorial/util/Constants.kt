package com.nuwandacreations.asuntosinstitucionalesinmemorial.util

class Constants {
    companion object {
        const val BASE_URL = "https://api.jsonbin.io/"
        const val EMPTY_NAME = "SIN NOMBRE"
        const val REGALOS = "regalos"
        const val MATERIAL = "material"
        const val EVENTOS = "eventos"
        const val STORAGE = "storage"
        const val GUESTS = "invitados"
        const val REGALOS_JSON = "0-otros/regalos.json"
        const val MATERIAL_JSON = "0-otros/material.json"
        const val GUESTS_JSON = "1-eventos/"
        const val EVENTS_PHOTOS_JPG = "1-eventos/imagenes/"
        const val GUESTS_PHOTOS_JPG = "1-eventos/imagenes/invitados/"
        const val GUESTS_TICKETS = "1-eventos/entradas/"
        const val GUESTS_BASE_TICKETS = "1-eventos/entradas/tickets-base/"
        const val DETAIL_EVENT_GUEST_BTN = "Subir invitados desde JSON"
        const val DETAIL_EVENT_DELETE_BTN = "Eliminar evento"
        const val DETAIL_EVENT_GENERATE_QR_BTN = "Generar invitaciones del evento"
        const val EVENT_ID = "Id del evento"
        const val EVENT_NAME = "Nombre del evento"
        const val EVENT_DATE = "Fecha del evento"
        const val EVENT_PLACE = "Lugar del evento"
        const val EVENT_IS_RELEVO_GUARDIA = "¿Es un Relevo de Guardia?"
        const val EVENT_BUTTON = "Guardar evento"
        const val EVENT_SCREEN_TITTLE = "Crear nuevo evento"
        const val CONFIRM = "Confirmar"
        const val CANCEL = "Cancelar"

        const val AFIRMATIVE = "Sí"
        const val NEGATIVE = "No"
        //QR Scanner
        const val SCANNER = "scanner"
        const val QR_SCANNER_TITTLE = "Escanea el código QR"
        const val PERMISSION_NECESARY = "Permiso de cámara necesario"
        const val PERMISSION_APP_NECESARY = "Esta app necesita acceso a la cámara para escanear códigos QR."
        const val PERMISSION_ACCEPT = "Conceder permiso"
        const val PERMISSION_DENY = "Permiso denegado"
        const val PERMISSION_FROM_SETTINGS = "Has denegado el permiso de cámara. Para usar el escáner QR, debes habilitarlo en la configuración de la app."
        const val OPEN_SETTINGS = "Abrir configuración"
        const val TICKET_TEXT = "INVITACIÓN INDIVIDUAL"

        //ERROR
        const val ERROR_UPDATING_EVENT = "Error actualizando evento"
        const val ERROR_GETTING_GUESTS = "Error al obtener los invitados del evento"
        const val ERROR_LOADING_GUEST = "Error al cargar invitado"
        const val ERROR_UNBINDING_CAMERA = "Error al liberar cámara"
        const val ERROR_STARTING_CAMERA = "Error al iniciar cámara"
        const val ERROR_VALIDATING_GUEST = "Invitación no localizada en la base de datos"
        const val ERROR_SCANNING_QR = "Error al escanear"
        const val ERROR_GENERATING_QR = "Error al generar lo códigos QR"
        const val ERROR_GENERATING_PDF = "Error al convertir a PDF"

    }
}