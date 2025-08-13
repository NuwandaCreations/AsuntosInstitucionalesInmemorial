package com.example.asuntosinstitucionalesinmemorial.ui.core.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.asuntosinstitucionalesinmemorial.R

@Composable
fun NavigationBar(navController: NavHostController) {
    val modifier = Modifier.size(20.dp)
    val items = listOf(
        NavItem("Home", R.drawable.ic_home, Home),
        NavItem("Eventos", R.drawable.ic_events, Events),
        NavItem("Regalos", R.drawable.ic_present, RegalosStorage),
        NavItem("Material", R.drawable.ic_material, MaterialStorage)

    )
    NavigationBar(containerColor = Color.Black) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(item.icon), contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentDestination?.route?.substringAfterLast('.') == item.screen.route,
                modifier = modifier,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.white),
                    selectedTextColor = colorResource(id = R.color.white),
                    indicatorColor = colorResource(id = R.color.onPrimary),
                    unselectedIconColor = colorResource(id = R.color.onPrimary),
                    unselectedTextColor = colorResource(id = R.color.onPrimary),
                ),
                onClick = {
                    navController.navigate(item.screen) {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}

data class NavItem(val label: String, val icon: Int, val screen: AppScreen)