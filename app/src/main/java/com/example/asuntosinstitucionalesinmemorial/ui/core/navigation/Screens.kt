package com.example.asuntosinstitucionalesinmemorial.ui.core.navigation

import com.example.asuntosinstitucionalesinmemorial.ui.home.ButtonAction
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
data class EventGuests(val event: String) : AppScreen {
    override val route = "EventGuests"
}
