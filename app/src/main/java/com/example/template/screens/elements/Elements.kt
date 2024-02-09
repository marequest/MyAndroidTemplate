package com.example.template.screens.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun LabeledRow(label: String, value: String) {
    var textBoxValue by remember { mutableStateOf(value) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(0.7f) // Allocates space based on weight, pushing the label to the left
        )
        Text(
            text = value,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier.weight(1.3f) // Ensures the value starts from the same position across all rows
        )
//        OutlinedTextField(
//            value = textBoxValue,
//            enabled = false,
//            onValueChange = { textBoxValue = it },
//            label = {Text(label)},
//            modifier = Modifier.weight(1f)
//        )
    }
}

@Composable
fun DropdownTab(title: String, expanded: Boolean, onTabClick: () -> Unit, content: @Composable () -> Unit) {
    val elevation = if (expanded) 8.dp else 2.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(elevation, RoundedCornerShape(8.dp)), // Apply shadow for elevation effect
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.clickable { onTabClick() }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(Modifier.padding(horizontal = 12.dp)) {
                    content()

                }
            }
        }
    }
}


@Composable
fun MyBigText(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun HorizontalLineSpacer(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth() // Fill the maximum width available
            .height(1.dp) // Set the height to 1dp to create a thin line
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)) // Use a semi-transparent color for the line
    )
}

@Composable
fun TimeSelectionRow() {
    var fromTime by remember { mutableStateOf(LocalTime.now().withMinute(0).withSecond(0).withNano(0)) }
    var toTime by remember { mutableStateOf(LocalTime.now().withMinute(0).withSecond(0).withNano(0).plusHours(1)) }
    var ukupnoSatiValue by remember { mutableStateOf("") }

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
//        Text(text = smena)
        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = {
            // Trigger TimePickerDialog for 'From' time
        }) {
            Text(text = "Od: ${fromTime.format(timeFormatter)}")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = {
            // Trigger TimePickerDialog for 'To' time
        }) {
            Text(text = "Do: ${toTime.format(timeFormatter)}")
        }
        Spacer(modifier = Modifier.width(12.dp))

        OutlinedTextField(
            value = ukupnoSatiValue,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) { // Ensure only digits are taken
                    ukupnoSatiValue = newValue
                }
            },
            enabled = false,
            label = { Text("Ukupno") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f) // Each TextField takes up equal space
        )
    }
}
