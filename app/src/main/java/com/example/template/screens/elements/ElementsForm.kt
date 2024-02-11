package com.example.template.screens.elements

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun LabeledRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(0.9f) // Allocates space based on weight, pushing the label to the left
        )
        Text(
            text = value,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier.weight(1.1f) // Ensures the value starts from the same position across all rows
        )
    }
}

@Composable
fun MyHeaderText(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.W400,
        modifier = Modifier.padding(top = 8.dp)
    )
    HorizontalLineSpacer(modifier = Modifier.padding(top = 0.dp))
}

@Composable
fun DropdownTab(title: String, expanded: Boolean, onTabClick: () -> Unit, content: @Composable () -> Unit) {
    val elevation = if (expanded) 8.dp else 2.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(elevation, RoundedCornerShape(8.dp)), // Apply shadow for elevation effect
        shape = RoundedCornerShape(0.dp)
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
    var ukupnoSatiValue by remember { mutableStateOf("8") }

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp)) {
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

@Composable
fun TimeTemperatureRow(index: Int) {
    var time by remember { mutableStateOf(LocalTime.now()) }
    var temperature by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Button to show and update time
        Button(
            onClick = {
                // Trigger TimePickerDialog for 'From' time
            },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(text = time.format(DateTimeFormatter.ofPattern("HH:mm")))
        }

        // Outlined text field for temperature input
        OutlinedTextField(
            value = temperature,
            onValueChange = { newValue ->
                // Update only if the new value is a float number or empty
                if (newValue.toFloatOrNull() != null || newValue.isEmpty()) {
                    temperature = newValue
                }
            },
            label = { Text("Temperatura (°C)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f) // Take up remaining space
        )
    }
}

@Composable
fun TripleInputRow(text: String) {
    // States for each input field in a row
    var input1 by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // First OutlinedTextField
        OutlinedTextField(
            value = input1,
            onValueChange = { input1 = it },
            label = { Text(text) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeDescriptionTextField() {
    var text by remember { mutableStateOf("") }

    // Calculate the approximate height for 5 rows. Adjust the multiplier as needed for your font size and padding.
    val minHeight = 240.dp // Assuming each row is roughly 24.dp in height, adjust based on your actual text size and padding.

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = { Text("Opis Rada") },
        placeholder = { Text("Opis Rada") },
        singleLine = false,
        maxLines = 8, // Makes the text field multiline, but visually restricts it to 5 lines initially.
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = minHeight) // Ensures the text field has a minimum height equivalent to 5 rows.
            .padding(0.dp),
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { /* Handle the 'Done' action */ }),
        colors = TextFieldDefaults.outlinedTextFieldColors()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargePrimedbeTextField() {
    var text by remember { mutableStateOf("") }

    // Calculate the approximate height for 5 rows. Adjust the multiplier as needed for your font size and padding.
    val minHeight = 240.dp // Assuming each row is roughly 24.dp in height, adjust based on your actual text size and padding.

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = { Text("Primedbe") },
        placeholder = { Text("Primedbe") },
        singleLine = false,
        maxLines = 8, // Makes the text field multiline, but visually restricts it to 5 lines initially.
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = minHeight) // Ensures the text field has a minimum height equivalent to 5 rows.
            .padding(0.dp),
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { /* Handle the 'Done' action */ }),
        colors = TextFieldDefaults.outlinedTextFieldColors()
    )
}

@Composable
fun NumberInputLayout() {

    Column {
//        MyBigText(text = "I Smena")
        Row(modifier = Modifier
            .padding(vertical = 0.dp)
            .fillMaxWidth()) {
            var number1 by remember { mutableStateOf("") }
            var number2 by remember { mutableStateOf("") }

            OutlinedTextField(
                value = number1,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) { // Ensure only digits are taken
                        number1 = newValue
                    }
                },
                label = { Text("Gradjevinski radnici") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f) // Each TextField takes up equal space
                    .padding(end = 8.dp)
            )
            OutlinedTextField(
                value = number2,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) { // Ensure only digits are taken
                        number2 = newValue
                    }
                },
                label = { Text("Zanatlije") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f) // Each TextField takes up equal space
            )
        }
        Row(modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()) {
            var number3 by remember { mutableStateOf("") }
            var number4 by remember { mutableStateOf("") }

            OutlinedTextField(
                value = number3,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) { // Ensure only digits are taken
                        number3 = newValue
                    }
                },
                label = { Text("Tehnicko osoblje") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f) // Each TextField takes up equal space
                    .padding(end = 8.dp) // Add some horizontal padding between TextFields
            )
            OutlinedTextField(
                value = number4,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) { // Ensure only digits are taken
                        number4 = newValue
                    }
                },
                label = { Text("Ostali") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f) // Each TextField takes up equal space
            )
        }

        // Second row with a non-clickable element that spans the full width
        OutlinedTextField(
            value = "0",
            onValueChange = {}, // No action on value change
            label = { Text("Ukupno") },
            enabled = false, // Makes the text field non-editable and non-clickable
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun FilePicker() {
    var fileName by remember { mutableStateOf<String?>(null) }

    // Prepare the launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        // Handle the returned Uri, extract file name
        uri?.let {
            fileName = it.lastPathSegment // This gets the file name. Depending on the file and source, you might need to handle this differently
        }
    }

    Column(modifier = Modifier.padding(top = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Dodajte Prilog:  ")
            Button(modifier = Modifier.weight(1f), onClick = { filePickerLauncher.launch("*/*") }) { // Launches file picker, "*" indicates any file type
                Text("Dodajte Fajl")
            }
        }

        // Display the selected file name
        fileName?.let {
            Text(text = "Odabrani fajl: $it")
        }
    }
}

