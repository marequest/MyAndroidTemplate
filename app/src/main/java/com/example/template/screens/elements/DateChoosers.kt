package com.example.template.screens.elements

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.text.DateFormatSymbols
import java.time.LocalDate
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaySelector() {
    val calendar = Calendar.getInstance()
    // Adjust the index to make the week start from Monday
    val currentDayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7
    val daysOfWeekEnglish = DateFormatSymbols().weekdays
        .toList()
        .filter { it.isNotEmpty() }
        .let { it.drop(1) + it.first() } // Move Sunday to the end

    val daysOfWeekSerbian = listOf("Ponedeljak", "Utorak", "Sreda", "Cetvrtak", "Petak", "Subota", "Nedelja")

    var expanded by remember { mutableStateOf(false) }
    // Use the Serbian name for the initially selected day
    var selectedDay by remember { mutableStateOf(daysOfWeekSerbian[currentDayOfWeek]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            readOnly = true,
            value = "$selectedDay",
            onValueChange = { },
            label = { Text("Dan") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            daysOfWeekSerbian.forEachIndexed { index, day ->
                DropdownMenuItem(
                    text = { Text(text = day) }, // Text(text = "${daysOfWeekEnglish[index]} - $day")
                    onClick = {
                        selectedDay = day
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DateChooser() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current

    // Button to trigger dialog
    Button(onClick = { showDialog = true }) {
        Text("Datum: ${selectedDate.toString()}")
    }

    // Show dialog
    if (showDialog) {
        val onDateSetListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            showDialog = false
        }

        val datePickerDialog = DatePickerDialog(
            context,
            onDateSetListener,
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth
        )

        // Prevent dialog from being dismissed on outside touch
        datePickerDialog.setCancelable(false)

        // Show the DatePickerDialog
        LaunchedEffect(Unit) {
            datePickerDialog.show()
        }

        // Dismiss the dialog when showDialog becomes false
        DisposableEffect(Unit) {
            onDispose {
                datePickerDialog.dismiss()
            }
        }
    }
}