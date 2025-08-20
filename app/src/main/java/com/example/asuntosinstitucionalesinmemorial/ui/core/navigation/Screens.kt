package com.example.asuntosinstitucionalesinmemorial.ui.core.navigation

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
object StorageDetail : AppScreen {
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
