/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.template.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Preview
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.template.R

object NavDestinations {
    const val LOGIN = "Login"
    const val REGISTRATION_SCREEN = "RegistrationScreen"
    const val DNEVNICI_LIST_SCREEN = "DnevniciListScreen"
    const val DNEVNIK_SETTINGS_SCREEN = "DnevnikSettingsScreen"
    const val DNEVNIK_FORM_SCREEN = "DnevniciFormScreen/{dnevnikId}"
    const val PROFILE_SCREEN = "ProfileScreen"

}
data class TemplateTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)
class TemplateNavigationActions(private val navController: NavHostController) {
    fun navigateToByDnenikId(destination: TemplateTopLevelDestination, dnevnikId: String? = null) {
        val route = if (dnevnikId != null) {
            destination.route.replace("{dnevnikId}", dnevnikId)
        } else {
            destination.route
        }

        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateTo(destination: TemplateTopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    fun navigateTo(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}


val TOP_LEVEL_DESTINATIONS = listOf(
    TemplateTopLevelDestination(
        route = NavDestinations.DNEVNICI_LIST_SCREEN,
        selectedIcon = Icons.Default.List,
        unselectedIcon = Icons.Default.List,
        iconTextId = R.string.dnevnici
    ),
    TemplateTopLevelDestination(
        route = NavDestinations.DNEVNIK_SETTINGS_SCREEN,
        selectedIcon = Icons.Outlined.Preview,
        unselectedIcon = Icons.Outlined.Preview,
        iconTextId = R.string.projekat
    ),
    TemplateTopLevelDestination(
        route = NavDestinations.DNEVNIK_FORM_SCREEN,
        selectedIcon = Icons.Default.InsertDriveFile,
        unselectedIcon = Icons.Default.InsertDriveFile,
        iconTextId = R.string.dnevnik
    ),
    TemplateTopLevelDestination(
        route = NavDestinations.PROFILE_SCREEN,
        selectedIcon = Icons.Default.Person,
        unselectedIcon = Icons.Default.Person,
        iconTextId = R.string.profile
    )
)
