import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.widget.DatePicker
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar
import java.util.Locale

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties
import java.text.DateFormatSymbols


@Composable
fun Testing() {
//    Column(modifier = Modifier
//        .verticalScroll(rememberScrollState())
//        .padding(16.dp)
//    ) {
//        FirstPage()
//        SecondPage()
//        ThirdPage()
//}
    Scaffold(
        floatingActionButton = { InfoFABWithDialog() }
    ) { innerPadding ->
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(innerPadding)) {
            FirstPage()
            SecondPage()
            ThirdPage()
        }
    }
}

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


@Composable
fun FirstPage() {
    MyBigText(text = "Radno Vreme")
    Spacer(modifier = Modifier.height(12.dp))
    DropdownTabsTimeInput()
    HorizontalLineSpacer(modifier = Modifier.padding(top = 24.dp))
}

@Composable
fun SecondPage() {
    MyBigText(text = "Broj Radnika")
    Spacer(modifier = Modifier.height(12.dp))
    DropdownTabsNumberInput()
    HorizontalLineSpacer(modifier = Modifier.padding(top = 16.dp))
}

@Composable
fun ThirdPage() {

    MyBigText(text = "Gradjevinski Dnevnik")
    Spacer(modifier = Modifier.height(12.dp))
    DateAndDaySelector()

}

@Composable
fun DateAndDaySelector() {

    DaySelector()
    Spacer(modifier = Modifier.height(16.dp))
    DateChooser()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaySelector() {
    val calendar = Calendar.getInstance()
    val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val daysOfWeek = DateFormatSymbols().weekdays.filter { it.isNotEmpty() }
    var expanded by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf(daysOfWeek[currentDayOfWeek - 1]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                    println("Dropdown clicked, expanded: $expanded")
                }
        ) {
            TextField(
                readOnly = true,
                value = selectedDay,
                onValueChange = { },
                label = { Text("Select Day") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            daysOfWeek.forEach { day ->
                DropdownMenuItem(
                    text = {Text(text = day)},
                    onClick = {
                        selectedDay = day
                        expanded = false
                        println("Item clicked: $day")
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

@Composable
fun LabeledRows() {
    Column {
        LabeledRow(label = "Izvodjac radova", value = "Serbia projekt inzenjering doo")
        LabeledRow(label = "Objekat", value = "Izgradnja i rekonstrukcija poletno sletnih staza")
        LabeledRow(label = "Mesto", value = "Surcin")
        LabeledRow(label = "Investitor" , value = "AD Aerodrop Nikola Tesla Beorad")
        LabeledRow(label = "Adresa" , value = "11180 Beograd 59")
    }
}

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
            onValueChange = { ukupnoSatiValue = it },
            label = { Text("Ukupno") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun NumberInputLayout() {

    Column {
//        MyBigText(text = "I Smena")
        Row(modifier = Modifier
            .padding(vertical = 8.dp)
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
                label = { Text("Gradjevinski radnici", fontSize = 13.sp) },
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
                label = { Text("Zanatlije", fontSize = 13.sp) },
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
                label = { Text("Tehnicko osoblje", fontSize = 13.sp) },
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
                label = { Text("Ostali", fontSize = 13.sp) },
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
fun DropdownTabsTimeInput() {
    var expandedTab by remember { mutableStateOf<Int?>(0) } // Initially, the first tab is expanded. Use null for no tab expanded.

    Column() {
        // Tab 1
        DropdownTab(
            title = "I Smena",
            expanded = expandedTab == 0,
            onTabClick = { expandedTab = if (expandedTab == 0) null else 0 }
        ) {
            TimeSelectionRow()
        }

        // Tab 2
        DropdownTab(
            title = "II Smena",
            expanded = expandedTab == 1,
            onTabClick = { expandedTab = if (expandedTab == 1) null else 1 }
        ) {
            TimeSelectionRow()
        }

        // Tab 3
        DropdownTab(
            title = "III Smena",
            expanded = expandedTab == 2,
            onTabClick = { expandedTab = if (expandedTab == 2) null else 2 }
        ) {
            TimeSelectionRow()
        }
    }
}

@Composable
fun DropdownTabsNumberInput() {
    var expandedTab by remember { mutableStateOf<Int?>(0) } // Initially, the first tab is expanded. Use null for no tab expanded.

    Column() {
        // Tab 1
        DropdownTab(
            title = "I Smena",
            expanded = expandedTab == 0,
            onTabClick = { expandedTab = if (expandedTab == 0) null else 0 }
        ) {
            NumberInputLayout()
        }

        // Tab 2
        DropdownTab(
            title = "II Smena",
            expanded = expandedTab == 1,
            onTabClick = { expandedTab = if (expandedTab == 1) null else 1 }
        ) {
            NumberInputLayout()
        }

        // Tab 3
        DropdownTab(
            title = "III Smena",
            expanded = expandedTab == 2,
            onTabClick = { expandedTab = if (expandedTab == 2) null else 2 }
        ) {
            NumberInputLayout()
        }
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

//@Composable
//fun DropdownTab(title: String, expanded: Boolean, onTabClick: () -> Unit, content: @Composable () -> Unit) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(MaterialTheme.colorScheme.primaryContainer)
//            .clickable { onTabClick() }
//            .padding(10.dp)
//    ) {
//        Text(text = title, modifier = Modifier.weight(1f))
//        Icon(
//            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
//            contentDescription = if (expanded) "Collapse" else "Expand",
//            tint = MaterialTheme.colorScheme.onSecondaryContainer
//        )
//    }
//
//    AnimatedVisibility(
//        visible = expanded,
//        enter = expandVertically() + fadeIn(),
//        exit = shrinkVertically() + fadeOut()
//    ) {
//        content()
//    }
//}

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
