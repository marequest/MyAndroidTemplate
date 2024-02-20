package com.example.template.pages.elements

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var selectedDay by remember { mutableStateOf(daysOfWeekSerbian[currentDayOfWeek]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
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
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
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

    Text(
        text = "Datum: ${selectedDate.toString()}",
        fontSize = 24.sp,
        modifier = Modifier.padding(8.dp).clickable { showDialog = true },
        color = MaterialTheme.colorScheme.primary
    )

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
