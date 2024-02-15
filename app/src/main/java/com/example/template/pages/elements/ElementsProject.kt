package com.example.template.pages.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DownloadPDF() {
    OutlinedButton(
        onClick = {
            // Add your save action here
        },
        border = BorderStroke(1.dp, Color.Red),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Red),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(imageVector = Icons.Filled.Download, contentDescription = "Download")
        Text(" Download PDF")
    }
}