package com.example.template.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TabScreenWithProgress() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Tab 1", "Tab 2", "Tab 3")
    val tabIcons = listOf(Icons.Filled.Home, Icons.Filled.ShoppingCart, Icons.Filled.Settings)

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.Black
        ) {
            tabs.forEachIndexed { index, text ->
                Tab(
                    text = { Text(text) },
                    icon = { Icon(tabIcons[index], contentDescription = null) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        // Progress bar that spans from left to right based on the selected tab
        LinearProgressIndicator(
            progress = (selectedTabIndex + 1) / tabs.size.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = Color.Blue
        )

        // Content for each tab
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTabIndex) {
                0 -> TabContent("Content for Tab 1")
                1 -> TabContent("Content for Tab 2")
                2 -> TabContent("Content for Tab 3")
            }
        }
    }
}

@Composable
fun TabContent(text: String) {
    Text(text = text, style = MaterialTheme.typography.bodyMedium)
}
