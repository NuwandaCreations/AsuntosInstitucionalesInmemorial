package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.navigation

import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.home.ButtonAction
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppScreen {
    val route: String
}

@Serializable
object Home : AppScreen {
    override val route = "Home"
}

@Serializable
object RegalosStorage : AppScreen {
    override val route = "RegalosStorage"
}

@Serializable
object MaterialStorage : AppScreen {
    override val route = "MaterialStorage"
}

@Serializable
data class StorageDetail(val objeto: String, val type: ButtonAction) : AppScreen {
    override val route = "StorageDetail"
}

@Serializable
object EditStorage : AppScreen {
    override val route = "EditStorage"
}

@Serializable
object Events : AppScreen {
    override val route = "Events"
}

@Serializable
data class EventDetail(val event: String) : AppScreen {
    override val route = "EventDetail"
}

@Serializable
object CreateEvent : AppScreen {
    override val route = "CreateEvent"
}

@Serializable
data class EventGuests(val event: String, val isRelevo: Boolean, val filterGuestsByGroup: String = "") : AppScreen {
    override val route = "EventGuests"
}

@Serializable
data class GuestCountAccessed(val event: String, val isRelevo: Boolean) : AppScreen {
    override val route = "GuestCountAccessed"
}

@Serializable
data class QrScanner(val event: String, val isRelevo: Boolean) : AppScreen {
    override val route = "QrScanner"
}

@Serializable
data class GuestDetail(val event: String, val guest: String, val isRelevo: Boolean, val hasQrScanned: Boolean) : AppScreen {
    override val route = "GuestDetail"
}