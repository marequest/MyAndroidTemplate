package com.example.template.screens.elements

import LabeledRows
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.DialogProperties

@Composable
fun InfoFABWithDialog() {
    var showDialog by remember { mutableStateOf(false) }

    // Floating Action Button with Info Icon
    FloatingActionButton(onClick = { showDialog = true }) {
        Icon(Icons.Filled.Info, contentDescription = "Info")
    }

    // Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("U redu")
                }
            },
            title = { Text("Informacije") },
            text = {
                // Your component inside the dialog
                LabeledRows()
            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        )
    }
}