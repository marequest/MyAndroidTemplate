import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import com.example.template.screens.elements.DateChooser
import com.example.template.screens.elements.DaySelector
import com.example.template.screens.elements.DropdownTab
import com.example.template.screens.elements.FilePicker
import com.example.template.screens.elements.HorizontalLineSpacer
import com.example.template.screens.elements.LabeledRow
import com.example.template.screens.elements.LargeDescriptionTextField
import com.example.template.screens.elements.LargePrimedbeTextField
import com.example.template.screens.elements.MyHeaderText
import com.example.template.screens.elements.NumberInputLayout
import com.example.template.screens.elements.TimeSelectionRow
import com.example.template.screens.elements.TimeTemperatureRow
import com.example.template.screens.elements.TripleInputRow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Testing() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        floatingActionButton = { InfoFABWithDialog() },
        topBar = { SimpleTopAppBar(scrollBehavior = scrollBehavior, "Gradjevinski Dnevnik") }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(16.dp)
            .padding(innerPadding)
        ) {
            HorizontalLineSpacer(modifier = Modifier.padding(top = 8.dp))
            TabScreenWithProgress(selectedTabIndex) { selectedTabIndex = it }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(scrollBehavior: TopAppBarScrollBehavior, text: String) {

    TopAppBar(
        title = { Text(text) },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun TabScreenWithProgress(selectedTabIndex: Int, changeSelectedIndex: (Int) -> Unit) {
    val tabs = listOf("Korak 1", "Korak 2", "Korak 3")
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
                    text = { Text(tabs[index])},
//                    icon = { Icon(tabIcons[index], contentDescription = null) },
                    selected = selectedTabIndex == index,
                    onClick = { changeSelectedIndex(index) }
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
                    FirstPage()
                }
                1 -> {
                    SecondPage()
                }
                2 -> {
                    ThirdPage()
                }
            }
        }
    }
}


@Composable
fun FirstPage() {
    MyHeaderText(text = "Radno Vreme")
    Spacer(modifier = Modifier.height(12.dp))
    DropdownTabsRadnoVremeInput()
//    HorizontalLineSpacer(modifier = Modifier.padding(top = 24.dp))
    MyHeaderText(text = "Broj Radnika")
    Spacer(modifier = Modifier.height(12.dp))
    DropdownTabsBrojRadnikaInput()
}

@Composable
fun SecondPage() {
    DateAndDaySelector()
//    HorizontalLineSpacer(modifier = Modifier.padding(top = 16.dp))
    TimeTemperatureRows()
//    HorizontalLineSpacer(modifier = Modifier.padding(top = 16.dp))
    TripleInputRows()
}

@Composable
fun ThirdPage() {
    LargeDescriptionTextField()
    LargePrimedbeTextField()
//    HorizontalLineSpacer(modifier = Modifier.padding(top = 16.dp))
    FilePicker()
//    HorizontalLineSpacer(modifier = Modifier.padding(top = 16.dp))
    MyHeaderText(text = "Informacije")
    LabeledRow(label = "Vode Dnevnik", value = "")
    LabeledRow(label = "Izvodjac Radova", value = "Potpis")
    LabeledRow(label = "Nadzorni Organ", value = "")
}

@Composable
fun TimeTemperatureRows() {
    // Repeat 3 times for 3 rows
    repeat(3) { index ->
        TimeTemperatureRow(index = index)
    }
}

@Composable
fun TripleInputRows() {
    TripleInputRow(text = "suncano, oblacno, kisa...")
    TripleInputRow(text = "brzina vetra")
    TripleInputRow(text = "nivo podzemnih voda")
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
fun DropdownTabsRadnoVremeInput() {
    var expandedTab by remember { mutableStateOf<Int?>(0) } // Initially, the first tab is expanded. Use null for no tab expanded.

    Column() {
        DropdownTab(
            title = "I Smena",
            expanded = expandedTab == 0,
            onTabClick = { expandedTab = if (expandedTab == 0) null else 0 }
        ) {
            TimeSelectionRow()
        }
        DropdownTab(
            title = "II Smena",
            expanded = expandedTab == 1,
            onTabClick = { expandedTab = if (expandedTab == 1) null else 1 }
        ) {
            TimeSelectionRow()
        }
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
fun DropdownTabsBrojRadnikaInput() {
    var expandedTab by remember { mutableStateOf<Int?>(0) } // Initially, the first tab is expanded. Use null for no tab expanded.

    Column() {
        DropdownTab(
            title = "I Smena",
            expanded = expandedTab == 0,
            onTabClick = { expandedTab = if (expandedTab == 0) null else 0 }
        ) {
            NumberInputLayout()
        }
        DropdownTab(
            title = "II Smena",
            expanded = expandedTab == 1,
            onTabClick = { expandedTab = if (expandedTab == 1) null else 1 }
        ) {
            NumberInputLayout()
        }
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