package com.example.template.pages.elements

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.abs
import kotlin.math.ceil


@Composable
fun CustomTabRow(
    context: Context,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onLeftArrowClick: () -> Unit,
    leftArrowEnabled: Boolean,
    onRightArrowClick: () -> Unit,
    rightArrowEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(enabled = leftArrowEnabled, onClick = onLeftArrowClick) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Left Arrow")
        }
        Spacer(Modifier.weight(1f))
        DateChooser(
            context = context,
            showDialog = showDialog,
            onShowDialogChange = onShowDialogChange,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected
        )
        Spacer(Modifier.weight(1f))
        IconButton(enabled = rightArrowEnabled, onClick = onRightArrowClick) {
            Icon(Icons.Filled.ArrowForward, contentDescription = "Right Arrow")
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(scrollBehavior: TopAppBarScrollBehavior, text: String) {

    TopAppBar(
        title = { Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.W800,
            color = Color.White,
            modifier = Modifier.padding(vertical = 8.dp)
        ) },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
        scrollBehavior = scrollBehavior,
    )
}


@Composable
fun DropdownTab(title: String, expanded: Boolean, onTabClick: () -> Unit, content: @Composable () -> Unit) {
    val elevation = if (expanded) 8.dp else 3.dp

    Card(
//        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
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
fun HorizontalLineSpacer(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth() // Fill the maximum width available
            .height(1.dp) // Set the height to 1dp to create a thin line
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)) // Use a semi-transparent color for the line
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TimeSelectionRow() {
    var prviMinut by remember { mutableStateOf(LocalTime.now().hour * 60 + LocalTime.now().minute) }
    var drugiMinut by remember { mutableStateOf(LocalTime.now().hour * 60 + LocalTime.now().minute) }

    // Observe changes in prviMinut and drugiMinut and update ukupnoSatiValue accordingly
    val ukupnoSatiValue by derivedStateOf {
        if(prviMinut > drugiMinut){
            ceil(abs(prviMinut - drugiMinut - 24*60) / 60.0).toInt().toString()
        } else {
            ceil(abs(drugiMinut - prviMinut) / 60.0).toInt().toString()
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp)) {
        Spacer(modifier = Modifier.width(8.dp))
        // Assume TimePickerTextField is a custom composable that takes a callback
        // This is a placeholder for your actual TimePickerTextField implementation
        TimePickerTextField { newValue -> prviMinut = newValue }
        Spacer(modifier = Modifier.width(8.dp))
        TimePickerTextField { newValue -> drugiMinut = newValue }
        Spacer(modifier = Modifier.width(12.dp))

        OutlinedTextField(
            value = ukupnoSatiValue,
            onValueChange = {},
            enabled = false,
            label = { Text("Ukupno") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
fun TimeTemperatureRow(index: Int) {
//    var time by remember { mutableStateOf(LocalTime.now()) }
    var temperature by remember { mutableStateOf("") }
//    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        TimePickerTextField() {it1 -> {} }

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
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 8.dp)
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
        var number1 by remember { mutableStateOf("") }
        var number2 by remember { mutableStateOf("") }
        var number3 by remember { mutableStateOf("") }
        var number4 by remember { mutableStateOf("") }

        val numbers = listOf(
            Pair("Gradjevinski radnici", number1),
            Pair("Zanatlije", number2),
            Pair("Tehnicko osoblje", number3),
            Pair("Ostali", number4)
        )

        Column {
            numbers.forEachIndexed { index, pair ->
                val (label, value) = pair
                OutlinedTextField(
                    value = value,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) { // Ensure only digits are taken
                            when (index) {
                                0 -> number1 = newValue
                                1 -> number2 = newValue
                                2 -> number3 = newValue
                                3 -> number4 = newValue
                            }
                        }
                    },
                    label = { Text(label) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp) // Add padding to separate the TextFields
                )
            }
        }

        OutlinedTextField(
            value = number1 + number2 + number3 + number4,
            onValueChange = {}, // No action on value change
            label = { Text("Ukupno") },
            enabled = false,
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
            Button(
                modifier = Modifier.weight(1f),
                onClick = { filePickerLauncher.launch("*/*") }
            ) { // Launches file picker, "*" indicates any file type
                Icon(imageVector = Icons.Filled.UploadFile, contentDescription = null)
                Text(" Dodajte Fajl")
            }
        }

        // Display the selected file name
        fileName?.let {
            Text(text = "Odabrani fajl: $it")
        }
    }
}

@Composable
fun Stampaj() {
    OutlinedButton(
        onClick = {
            // Add your save action here
        },
        border = BorderStroke(1.dp, Color.Blue),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Blue),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(imageVector = Icons.Filled.Print, contentDescription = "Stampaj")
        Text(" Stampaj")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerTextField(/*time: LocalTime, timeFormatter: DateTimeFormatter, setTime: (LocalTime) -> Unit*/
                        onTimeSelected: (Int) -> Unit) {
    var showingDialog by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute,
        is24Hour = true
    )
    Text(
        text = "${timePickerState.hour}:${timePickerState.minute}",
        fontSize = 24.sp,
        modifier = Modifier
            .padding(8.dp)
            .clickable { showingDialog = true },
        color = MaterialTheme.colorScheme.primary
    )

    if (showingDialog) {
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
            onDismissRequest = { showingDialog = false }
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = Color.LightGray.copy(alpha = 0.3f)
                    )
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // time picker
                TimePicker(state = timePickerState)

                // buttons
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // dismiss button
                    TextButton(onClick = { showingDialog = false }) {
                        Text(text = "Dismiss")
                    }

                    // confirm button
                    TextButton(
                        onClick = {
                            showingDialog = false
                            onTimeSelected(timePickerState.hour * 60 + timePickerState.minute)
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}
