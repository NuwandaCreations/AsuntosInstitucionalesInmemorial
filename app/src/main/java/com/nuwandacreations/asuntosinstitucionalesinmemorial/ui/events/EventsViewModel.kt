package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.scale
import androidx.core.graphics.set
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.nuwandacreations.asuntosinstitucionalesinmemorial.R
import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.events.toDomain
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Evento
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Invitados
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage.GetEventGuestsStorageUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage.GetEventPhotoByIdStorageUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage.GetGuestsPhotosStorageUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage.GetRelevoGuestsStorageUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage.SetEventTicketStorageUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.DeleteEventFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventByIdFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventGuestsFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventsFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetRelevoGuestsFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetEventFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetGuestFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetRelevoGuestFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.ERROR_GENERATING_PDF
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.ERROR_GENERATING_QR
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.ERROR_GETTING_GUESTS
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.TICKET_TEXT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.Normalizer

class EventsViewModel(
    val getEventsFirestoreUseCase: GetEventsFirestoreUseCase,
    val getEventByIdFirestoreUseCase: GetEventByIdFirestoreUseCase,
    val getEventGuestsStorageUseCase: GetEventGuestsStorageUseCase,
    val getRelevoGuestsStorageUseCase: GetRelevoGuestsStorageUseCase,
    val getEventGuestsFirestoreUseCase: GetEventGuestsFirestoreUseCase,
    val getRelevoGuestsFirestoreUseCase: GetRelevoGuestsFirestoreUseCase,
    val setEventFirestoreUseCase: SetEventFirestoreUseCase,
    val setGuestFirestoreUseCase: SetGuestFirestoreUseCase,
    val setRelevoGuestFirestoreUseCase: SetRelevoGuestFirestoreUseCase,
    val setEventTicketStorageUseCase: SetEventTicketStorageUseCase,
    val getGuestsPhotosStorageUseCase: GetGuestsPhotosStorageUseCase,
    val getEventPhotoByIdStorageUseCase: GetEventPhotoByIdStorageUseCase,
    val deleteEventFirestoreUseCase: DeleteEventFirestoreUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState

    fun getEventsFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            getEventsFirestoreUseCase().collect { events ->
                val sortedEvents = events.sortedBy { it.fecha }
                _uiState.update { it.copy(events = sortedEvents.map { evento -> evento.toDomain() }) }
            }
        }
    }

    fun getEventByIdFirestore(event: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getEventByIdFirestoreUseCase(event).collect { evento ->
                _uiState.update { it.copy(event = evento.toDomain()) }
                getEventGuests(event, evento.esRelevoGuardia)
            }
        }
    }

    fun getEventPhotoByIdStorage(event: Evento) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                event.id?.let {
                    val photo = getEventPhotoByIdStorageUseCase(it)
                    if (photo.isNotEmpty()) {
                        setEventFirestoreUseCase(event.copy(imagen = photo))
                    }
                }
            } catch (_: Exception) {

            }
        }
    }

    fun getEventGuestsStorage(event: String, esRelevoGuardia: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            try {
                if (esRelevoGuardia) {
                    val invitadosRelevo = getRelevoGuestsStorageUseCase(event).map { invitado ->
                        invitado.toDomain()
                    }
                    if (invitadosRelevo.isNotEmpty()) {
                        invitadosRelevo.forEach { invitado ->
                            if (invitado.nombre.isNotEmpty()) {
                                setRelevoGuestFirestoreUseCase(event, invitado)
                            }
                        }
                        _uiState.update { it.copy(invitadosRelevo = invitadosRelevo) }
                    }
                } else {
                    val invitados = getEventGuestsStorageUseCase(event).map { invitado ->
                        invitado.toDomain()
                    }
                    if (invitados.isNotEmpty()) {
                        invitados.forEach { invitado ->
                            if (invitado.nombre.isNotEmpty()) {
                                setGuestFirestoreUseCase(event, invitado)
                            }
                        }
                        _uiState.update { it.copy(invitados = invitados) }
                    }
                }
            } catch (_: Exception) {

            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun deleteEventFirestore(eventId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteEventFirestoreUseCase(eventId)
            } catch (_: Exception) {
            }
        }
    }

    fun setGuestAccess(
        access: Boolean,
        isRelevo: Boolean,
        event: String
    ) {
        if (isRelevo) {
            _uiState.update {
                it.copy(
                    invitadoRelevoSelected = it.invitadoRelevoSelected.copy(
                        accedido = access
                    )
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    invitadoSelected = it.invitadoSelected.copy(
                        accedido = access
                    )
                )
            }
        }
        setGuestFirestore(
            event = event,
            esRelevoGuardia = isRelevo,
            invitadoRelevo = _uiState.value.invitadoRelevoSelected,
            invitado = _uiState.value.invitadoSelected
        )
    }

    fun setGuestFirestore(
        event: String,
        esRelevoGuardia: Boolean,
        invitado: Invitados? = null,
        invitadoRelevo: InvitadosRelevo? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (esRelevoGuardia) {
                    invitadoRelevo?.let {
                        setRelevoGuestFirestoreUseCase(event, it)
                    }
                } else {
                    invitado?.let {
                        setGuestFirestoreUseCase(event, it)
                    }
                }
            } catch (_: Exception) {

            }
        }
    }

    fun getEventGuests(event: String, esRelevoGuardia: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!esRelevoGuardia) {
                    getEventGuestsFirestoreUseCase(event).collect { invitados ->
                        _uiState.update {
                            it.copy(
                                invitados = invitados
                            )
                        }
                    }
                } else {
                    getRelevoGuestsFirestoreUseCase(event).collect { invitados ->
                        _uiState.update {
                            it.copy(
                                invitadosRelevo = invitados
                            )
                        }
                    }
                }

            } catch (e: Exception) {
                Log.e("GetEventGuests", ERROR_GETTING_GUESTS, e)
            }
        }
    }

    fun getGuestsPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val photoNames = getGuestsPhotosStorageUseCase()
                _uiState.update { it.copy(guestsPhotos = photoNames) }
            } catch (_: Exception) {
            }
        }
    }

    fun searchPhoto(
        event: String,
        invitado: Invitados = Invitados(),
        invitadoRelevo: InvitadosRelevo = InvitadosRelevo(),
        isRelevo: Boolean
    ) {
        val selectGuest = if (isRelevo) invitadoRelevo.nombre else invitado.nombre
        val upperName = selectGuest.uppercase()
        val normalized = Normalizer.normalize(upperName, Normalizer.Form.NFD)
        val photoName = normalized.replace("\\p{Mn}+".toRegex(), "")
        _uiState.value.guestsPhotos.forEach {
            if (photoName == it.first) {
                setGuestFirestore(
                    event = event,
                    esRelevoGuardia = isRelevo,
                    invitado = invitado.copy(foto = it.second),
                    invitadoRelevo = invitadoRelevo.copy(foto = it.second)
                )
            }
        }
    }

    fun selectGuest(
        invitado: Invitados = Invitados(),
        invitadoRelevo: InvitadosRelevo = InvitadosRelevo(),
        isRelevo: Boolean,
        isShortClick: Boolean = false
    ) {
        _uiState.update {
            if (isRelevo) {
                it.copy(invitadoRelevoSelected = invitadoRelevo, isDialogShown = isShortClick)
            } else {
                it.copy(invitadoSelected = invitado, isDialogShown = isShortClick)
            }
        }
    }

    fun generateGuestsQR(event: String, esRelevoGuardia: Boolean, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val guestsNames =
                    if (esRelevoGuardia) _uiState.value.invitadosRelevo.map { it.nombre }
                    else _uiState.value.invitados.map { it.nombre }
                val emptyTicketBitmap =
                    generateEmptyInvitationBitmap(eventName = event, context = context)
                guestsNames.forEachIndexed { index, guestName ->
                    val guestBitmap = generateQRCode(guestName)
                    val guestTicketBitmap = emptyTicketBitmap.copy(emptyTicketBitmap.config!!, true)

                    guestBitmap?.let { bitmap ->
                        val ticketBitmap = generateInvitationBitmap(
                            context = context,
                            guestName = guestName,
                            baseBitmap = guestTicketBitmap,
                            qrBitmap = bitmap
                        )
                        val invitationByteArray = convertBitmapToPdf(ticketBitmap)

                        setEventTicketStorageUseCase(
                            event = event,
                            ticket = Pair(invitationByteArray, guestName)
                        )
                        bitmap.recycle()
                        guestTicketBitmap.recycle()
                        ticketBitmap.recycle()

                        if (index % 5 == 0) {
                            System.gc()
                            delay(100)
                        }
                    }
                }
                emptyTicketBitmap.recycle()
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                Log.e("GenerateGuestsQR", ERROR_GENERATING_QR, e)
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun generateQRCode(text: String): Bitmap? {
        return try {
            val writer = QRCodeWriter()
            val qrSize = 250
            val hints = hashMapOf<EncodeHintType, Any>(
                EncodeHintType.CHARACTER_SET to "UTF-8",
                EncodeHintType.MARGIN to 1
            )
            val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, qrSize, qrSize, hints)
            val qrBitmap = createBitmap(qrSize, qrSize, Bitmap.Config.RGB_565)
            for (x in 0 until qrSize) {
                for (y in 0 until qrSize) {
                    qrBitmap[x, y] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
                }
            }
            qrBitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun generateEmptyInvitationBitmap(
        context: Context,
        eventName: String
    ): Bitmap {
        val width = 540
        val height = 680

        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)

        val backgroundPaint = Paint().apply {
            color = context.getColor(R.color.black)
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

        val backgroundImageBitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.ic_rinf1
        )
        val scaledWidth = 500
        val scaledHeight = 500
        val scaledBackground = backgroundImageBitmap.scale(scaledWidth, scaledHeight)
        canvas.drawBitmap(scaledBackground, (width - scaledWidth) / 2f, 145f, null)


        val eventNamePaint = Paint().apply {
            color = context.getColor(R.color.white)
            textSize = 70f
            typeface = ResourcesCompat.getFont(context, R.font.italianno_regular)
            textAlign = Paint.Align.CENTER
        }

        val detailsLines = eventName.split("\n")
        var yPosition = 80f
        for (line in detailsLines) {
            canvas.drawText(line, width / 2f, yPosition, eventNamePaint)
            yPosition += 90f
        }

        val ticketTextPaint = Paint().apply {
            color = context.getColor(R.color.white)
            textSize = 15f
            typeface = ResourcesCompat.getFont(context, R.font.inknut_antiqua_semibold)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(TICKET_TEXT, width / 2f, 130f, ticketTextPaint)

        return bitmap
    }

    fun generateInvitationBitmap(
        context: Context,
        guestName: String,
        baseBitmap: Bitmap,
        qrBitmap: Bitmap
    ): Bitmap {
        val width = 540
        val height = 680

        val bitmap = baseBitmap
        val canvas = Canvas(bitmap)

        val qrLeft = (width - 250) / 2f
        val qrTop = height - 400f
        canvas.drawBitmap(qrBitmap, qrLeft, qrTop, null)

        val guestNamePaint = Paint().apply {
            color = context.getColor(R.color.white)
            textSize = 25f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(guestName, width / 2f, height - 70f, guestNamePaint)

        return bitmap
    }

    private fun convertBitmapToPdf(bitmap: Bitmap): ByteArray {
        val pdfDocument = PdfDocument()
        var outputStream: ByteArrayOutputStream? = null

        try {
            val pageInfo = PdfDocument.PageInfo.Builder(
                bitmap.width,
                bitmap.height,
                1
            ).create()

            val page = pdfDocument.startPage(pageInfo)
            page.canvas.drawBitmap(bitmap, 0f, 0f, null)
            pdfDocument.finishPage(page)

            outputStream = ByteArrayOutputStream(32768)
            pdfDocument.writeTo(outputStream)

            return outputStream.toByteArray()
        } catch (e: IOException) {
            Log.e("ConvertBitmapToPdf", ERROR_GENERATING_PDF, e)
            return ByteArray(0)
        } finally {
            try {
                outputStream?.close()
                pdfDocument.close()
            } catch (e: Exception) {
                Log.e("ConvertBitmapToPdf", ERROR_GENERATING_PDF, e)
            }
        }
    }

    fun updateDialogType(dialogType: DialogType) {
        _uiState.update { it.copy(dialogType = dialogType) }
    }

    fun showDialog(isShown: Boolean) {
        _uiState.update { it.copy(isDialogShown = isShown) }
    }
}

data class EventsUiState(
    val events: List<Evento> = emptyList(),
    val event: Evento? = null,
    val invitados: List<Invitados> = emptyList(),
    val invitadoSelected: Invitados = Invitados(),
    val invitadosRelevo: List<InvitadosRelevo> = emptyList(),
    val invitadoRelevoSelected: InvitadosRelevo = InvitadosRelevo(),
    val guestsPhotos: List<Pair<String, String>> = emptyList(),
    val dialogType: DialogType? = null,
    val isDialogShown: Boolean = false,
    val isLoading: Boolean = false
)

enum class DialogType {
    SET_GUEST,
    DELETE_EVENT,
    GENERATE_QR
}