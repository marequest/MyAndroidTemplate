package com.example.template.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun ConstructionJournalForm() {
    var startTime1 by remember { mutableStateOf("") }
    var endTime1 by remember { mutableStateOf("") }

    var startTime2 by remember { mutableStateOf("") }
    var endTime2 by remember { mutableStateOf("") }

    var startTime3 by remember { mutableStateOf("") }
    var endTime3 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // Time Entry Row 1
        TimeEntryRow(
            startTime = startTime1,
            onStartTimeChange = { startTime1 = it },
            endTime = endTime1,
            onEndTimeChange = { endTime1 = it }
        )

        // Time Entry Row 2
        TimeEntryRow(
            startTime = startTime2,
            onStartTimeChange = { startTime2 = it },
            endTime = endTime2,
            onEndTimeChange = { endTime2 = it }
        )

        // Time Entry Row 3
        TimeEntryRow(
            startTime = startTime3,
            onStartTimeChange = { startTime3 = it },
            endTime = endTime3,
            onEndTimeChange = { endTime3 = it }
        )
    }
}

@Composable
fun TimeEntryRow(
    startTime: String,
    onStartTimeChange: (String) -> Unit,
    endTime: String,
    onEndTimeChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Start Time
        CustomTimePicker(
            selectedTime = startTime,
            onTimeSelected = { onStartTimeChange(it) },
            label = { Text("Start Time") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        // End Time
        CustomTimePicker(
            selectedTime = endTime,
            onTimeSelected = { onEndTimeChange(it) },
            label = { Text("End Time") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
    }
}

@Composable
fun CustomTimePicker(
    selectedTime: String,
    onTimeSelected: (String) -> Unit,
    label: @Composable (() -> Unit),
    modifier: Modifier = Modifier
) {
    val times = generateTimesWith15MinuteInterval()
    var showDialog by remember { mutableStateOf(false) }
    val selectedTimeIndex by remember { mutableIntStateOf(times.indexOf(selectedTime)) }

    Column(
        modifier = modifier
            .wrapContentHeight()
            .clickable { showDialog = true }
    ) {
        Box() {
            OutlinedTextField(
                modifier = Modifier.align(Alignment.TopCenter),
                value = selectedTime,
                onValueChange = { onTimeSelected(it) },
                label = label,
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.AccessTime, contentDescription = null)
                    }
                }
            )

            if (showDialog) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    ShowCustomTimePickerDialog(
                        times = times,
                        selectedTimeIndex = selectedTimeIndex,
                        onTimeSelected = {
                            onTimeSelected(it)
                            showDialog = false
                        },
                        onDismissRequest = { showDialog = false },
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowCustomTimePickerDialog(
    times: List<String>,
    selectedTimeIndex: Int,
    onTimeSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .border(2.dp, Color.Black)
        ,
        content = {
            Column(modifier = Modifier.height(300.dp)) {
                LazyColumn {
                    itemsIndexed(times) { index, time ->
                        Text(
                            text = time,
                            modifier = Modifier
                                .clickable {
                                    onTimeSelected(times[index])
                                }
                                .padding(16.dp)
                                .background(
                                    if (index == selectedTimeIndex) Color.Gray else Color.Transparent
                                )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismissRequest) {
                    Text("Cancel")
                }
            }
        }
    )
}

@Composable
fun generateTimesWith15MinuteInterval(): List<String> {
    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val calendar = Calendar.getInstance()
    val times = mutableListOf<String>()

    for (i in 0 until 24 * 4) { // 24 hours * 4 (15-minute intervals)
        times.add(formatter.format(calendar.time))
        calendar.add(Calendar.MINUTE, 15)
    }

    return times
}

@Preview(showBackground = true)
@Composable
fun ConstructionJournalFormPreview() {
    ConstructionJournalForm()
}
