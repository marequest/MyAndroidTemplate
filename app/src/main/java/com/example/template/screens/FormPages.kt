import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun Testing() {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(16.dp)
    ) {
        FirstPage()
        Spacer(modifier = Modifier.height(12.dp))
        SecondPage()
    }
}

@Composable
fun FirstPage() {
    Spacer(modifier = Modifier.height(24.dp))

    // Rows with labels
    LabeledRows()

    Spacer(modifier = Modifier.height(12.dp))

    // Larger text label
    MyBigText(text = "Radno Vreme")

    Spacer(modifier = Modifier.height(12.dp))

    TimeSelectionRows()
}

@Composable
fun SecondPage() {

    Column() {

        // Rows with labels
        LabeledRow(label = "Investitor" , value = "AD Aerodrop Nikola Tesla Beorad")
        LabeledRow(label = "Adresa" , value = "11180 Beograd 59")


        DropdownTabs()


        Spacer(modifier = Modifier.height(12.dp))
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
fun LabeledRows() {
    Column {
        LabeledRow(label = "Izvodjac radova", value = "Serbia projekt inzenjering doo")
        LabeledRow(label = "Objekat", value = "Izgradnja i rekonstrukcija poletno sletnih staza")
        LabeledRow(label = "Mesto", value = "Surcin")
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
//        Text(
//            text = label,
//            fontWeight = FontWeight.Bold,
//            fontSize = 16.sp,
//            modifier = Modifier.weight(0.8f) // Allocates space based on weight, pushing the label to the left
//        )
//        Text(
//            text = value,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp,
//            modifier = Modifier.weight(1.2f) // Ensures the value starts from the same position across all rows
//        )
        OutlinedTextField(
            value = textBoxValue,
            onValueChange = { textBoxValue = it },
            label = {Text(label)},
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TimeSelectionRows() {
    Column {
        TimeSelectionRow("I  ")
        TimeSelectionRow("II ")
        TimeSelectionRow("III")
    }
}

@Composable
fun TimeSelectionRow(smena: String) {
    var fromTime by remember { mutableStateOf(LocalTime.now().withMinute(0).withSecond(0).withNano(0)) }
    var toTime by remember { mutableStateOf(LocalTime.now().withMinute(0).withSecond(0).withNano(0).plusHours(1)) }
    var ukupnoSatiValue by remember { mutableStateOf("") }

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
        Text(text = smena)
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
fun DropdownTabs() {
    var expandedTab by remember { mutableStateOf(0) } // 0 means the first tab is expanded by default

    Column() {
        // Tab 1
        DropdownTab(
            title = "I Smena",
            expanded = expandedTab == 0,
            onTabClick = { expandedTab = 0 }
        ) {
            NumberInputLayout()
        }

        // Tab 2
        DropdownTab(
            title = "II Smena",
            expanded = expandedTab == 1,
            onTabClick = { expandedTab = 1 }
        ) {
            NumberInputLayout()
        }

        // Tab 3
        DropdownTab(
            title = "III Smena",
            expanded = expandedTab == 2,
            onTabClick = { expandedTab = 2 }
        ) {
            NumberInputLayout()
        }
    }
}

@Composable
fun DropdownTab(title: String, expanded: Boolean, onTabClick: () -> Unit, content: @Composable () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onTabClick() }
            .padding(10.dp)
    ) {
        Text(text = title, modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (expanded) Icons.Filled.ArrowLeft else Icons.Filled.ArrowRight,
            contentDescription = if (expanded) "Collapse" else "Expand",
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }

    AnimatedVisibility(
        visible = expanded,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        content()
    }
}