import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.widget.DatePicker
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import com.example.template.screens.TabContent
import com.example.template.screens.elements.DateChooser
import com.example.template.screens.elements.DaySelector
import com.example.template.screens.elements.DropdownTab
import com.example.template.screens.elements.HorizontalLineSpacer
import com.example.template.screens.elements.LabeledRow
import com.example.template.screens.elements.MyBigText
import com.example.template.screens.elements.TimeSelectionRow
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols


@Composable
fun Testing() {

    Scaffold(
        floatingActionButton = { InfoFABWithDialog() }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(16.dp)
            .padding(innerPadding)
        ) {
            MyBigText(text = "Gradjevinski Dnevnik")
            HorizontalLineSpacer(modifier = Modifier.padding(top = 8.dp))
            TabScreenWithProgress()
//            ThirdPage()
//            FirstPage()
//            SecondPage()
        }
    }
}

@Composable
fun TabScreenWithProgress() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Tab 1", "Tab 2", "Tab 3")
    val tabIcons = listOf(Icons.Filled.DateRange, Icons.Filled.AccessTime, Icons.Filled.People)

    val animatedProgress = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(selectedTabIndex) {
        coroutineScope.launch {
            animatedProgress.animateTo(
                targetValue = (selectedTabIndex + 1) / tabs.size.toFloat(),
                animationSpec = tween(durationMillis = 600)
            )
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { }, // Empty indicator
            contentColor = Color.Black
        ) {
            tabs.forEachIndexed { index, text ->
                Tab(
                    icon = { Icon(tabIcons[index], contentDescription = null) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        // Progress bar that spans from left to right based on the selected tab
        LinearProgressIndicator(
            progress = animatedProgress.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = MaterialTheme.colorScheme.secondary
        )

        // Content for each tab
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
        ) {
            when (selectedTabIndex) {
                0 -> {
                        ThirdPage()
                }
                1 -> {
                        FirstPage()
                }
                2 -> {
                        SecondPage()
                }
            }
        }
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

    Spacer(modifier = Modifier.height(12.dp))
    DateAndDaySelector()
    HorizontalLineSpacer(modifier = Modifier.padding(top = 16.dp))

}

@Composable
fun DateAndDaySelector() {

    DaySelector()
    Spacer(modifier = Modifier.height(16.dp))
    DateChooser()

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