package com.example.template.pages.screens

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Looks3
import androidx.compose.material.icons.filled.LooksOne
import androidx.compose.material.icons.filled.LooksTwo
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import com.example.template.fakedata.Smena
import com.example.template.fakedata.Strana
import com.example.template.fakedata.StraniceDataProvider
import com.example.template.pages.elements.CustomTabRow
import com.example.template.pages.elements.DateChooser
import com.example.template.pages.elements.DaySelector
import com.example.template.pages.elements.DropdownTab
import com.example.template.pages.elements.FilePicker
import com.example.template.pages.elements.HorizontalLineSpacer
import com.example.template.pages.elements.LabeledRow
import com.example.template.pages.elements.LargeDescriptionTextField
import com.example.template.pages.elements.LargePrimedbeTextField
import com.example.template.pages.elements.MyHeaderText
import com.example.template.pages.elements.NumberInputLayout
import com.example.template.pages.elements.SimpleTopAppBar
import com.example.template.pages.elements.Stampaj
import com.example.template.pages.elements.TimeSelectionRow
import com.example.template.pages.elements.TimeTemperatureRow
import com.example.template.pages.elements.TripleInputRow
import com.example.template.pages.elements.showToast
import com.example.template.viewmodels.StraniceScreenViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StranicaFormScreen(
    viewModel: StraniceScreenViewModel,
) {
    val changeStranica: (Long) -> Unit = {
        viewModel.updateStranaId(it)
    }
    val updateStranaDate: (Date) -> Unit = {
        viewModel.updateStranaDate(it)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    val strana = viewModel.uiState.collectAsState().value.strana
//    println(strana)


    strana?.let {
        Scaffold(
            topBar = { SimpleTopAppBar(scrollBehavior = scrollBehavior,
                "Dnevnik ${strana.dnevnikId} - Strana #${strana.stranaId}"
            )},
            floatingActionButton = {
                Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.padding(0.dp)) {
                    Column(horizontalAlignment = Alignment.End) {
                        InfoFABWithDialog()
                        Spacer(modifier = Modifier.padding(8.dp))
                        FloatingActionButton(
                            onClick = {
                                when (selectedTabIndex) {
                                    0 -> {
                                        selectedTabIndex = 1
                                    }
                                    1 -> {
                                        selectedTabIndex = 2
                                    }
                                    else -> { // Cuvaj Podatke
                                        Toast.makeText(context, "Sacuvano!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            containerColor = MaterialTheme.colorScheme.primary,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            if (selectedTabIndex == 2){
                                Row(Modifier.padding(horizontal = 20.dp)) {
                                    Icon(Icons.Filled.Save, "Sacuvaj")
                                    Text(modifier = Modifier.padding(top = 2.dp), text = "  Sacuvaj")
                                }
                            } else {
                                Icon(Icons.Filled.NavigateNext, "Sledeca Stranica")

                            }
                        }
                    }
                }
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.height(0.dp)
                ) {
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
            ) {
                TabScreenWithProgress(
                    strana = strana,
                    selectedTabIndex = selectedTabIndex,
                    changeSelectedIndex = { selectedTabIndex = it },
                    changeStranica = changeStranica,
                    updateStranaDate = updateStranaDate,
                    dnevnikId = strana.dnevnikId,
                    stranicaId = strana.stranaId
                )
            }
        }
    } ?: run {
        showToast(context, "GRESKA: Nije pronadjena strana!")
        EmptyComingSoon()
    }
}


@Composable
fun TabScreenWithProgress(
    strana: Strana,
    selectedTabIndex: Int,
    changeSelectedIndex: (Int) -> Unit,
    changeStranica: (Long) -> Unit,
    updateStranaDate: (Date) -> Unit,
    dnevnikId: Long,
    stranicaId: Long
) {
    val tabs = listOf("Korak 1", "Korak 2", "Korak 3")
    val tabIcons = listOf(Icons.Filled.LooksOne, Icons.Filled.LooksTwo, Icons.Filled.Looks3)

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

    val selectedDate = strana.datumStrane.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()


    var showDialog by remember { mutableStateOf(false) }
    // u TODO???? selectedDate ce biti datum dnevnika po dnevnikID-
//    var selectedDate by remember { mutableStateOf(stranicaDate) }
    val context = LocalContext.current


    Column(modifier = Modifier.fillMaxSize()) {
        CustomTabRow(
            context = context,
            showDialog = showDialog,
            onShowDialogChange = { showDialog = it },
            selectedDate = selectedDate,
            onDateSelected = { newDate ->
                updateStranaDate(Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
            },
            onLeftArrowClick = {
//                changeStranica((stranicaId.toInt().minus(1)).toString())
//                selectedDate = selectedStrana.datumStrane.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                changeStranica(stranicaId.minus(1))
            },
            leftArrowEnabled = stranicaId > 1,
            onRightArrowClick = {
                changeStranica(stranicaId.plus(1))
//                changeStranica((stranicaId.toInt() + 1).toString())
//                selectedDate = selectedStrana.datumStrane.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            },
            /// TODO Ovo ce trebati fix
            rightArrowEnabled = stranicaId != StraniceDataProvider.getLastStranica().stranaId

        )

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

        LinearProgressIndicator(
            progress = animatedProgress.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
        ) {
            when (selectedTabIndex) {
                0 -> {
                    FirstPage(
                        strana.smenaPrva,
                        strana.smenaDruga,
                        strana.smenaTreca
                    )
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
fun FirstPage(
    smenaPrva: Smena,
    smenaDruga: Smena,
    smenaTreca: Smena
) {
    MyHeaderText(text = "Radno Vreme")
    Spacer(modifier = Modifier.height(12.dp))
    DropdownTabsRadnoVremeInput(
        smenaPrva.datumOd, smenaPrva.datumDo,
        smenaDruga.datumOd, smenaDruga.datumDo,
        smenaTreca.datumOd, smenaTreca.datumDo
    )
    MyHeaderText(text = "Broj Radnika")
    Spacer(modifier = Modifier.height(12.dp))
    DropdownTabsBrojRadnikaInput(
        smenaPrva.brGradjRadnika, smenaPrva.brZanatlija, smenaPrva.brTehOsoblja, smenaPrva.brOstali,
        smenaDruga.brGradjRadnika, smenaDruga.brZanatlija, smenaDruga.brTehOsoblja, smenaDruga.brOstali,
        smenaTreca.brGradjRadnika, smenaTreca.brZanatlija, smenaTreca.brTehOsoblja, smenaTreca.brOstali
    )
}

@Composable
fun SecondPage() {
    DateAndDaySelector()
    HorizontalLineSpacer(modifier = Modifier.padding(top = 8.dp))
    TimeTemperatureRows()
    TripleInputRows()
}

@Composable
fun ThirdPage() {
    LargeDescriptionTextField()
    Spacer(modifier = Modifier.padding(4.dp))
    LargePrimedbeTextField()
    FilePicker()
    MyHeaderText(text = "Informacije")
    LabeledRow(label = "Vode Dnevnik", value = "")
    // TODO Dugme
    LabeledRow(label = "Izvodjac Radova", value = "Potpis")
    LabeledRow(label = "Nadzorni Organ", value = "")

    Spacer(modifier = Modifier.height(16.dp))
    Row {
        Column(Modifier.weight(1f)) {
            Stampaj()
        }
    }
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

    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current
    DateChooser(
        context = context,
        showDialog = showDialog,
        onShowDialogChange = { showDialog = it },
        selectedDate = selectedDate,
        onDateSelected = { newDate ->
            selectedDate = newDate
        }
    )
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
fun DropdownTabsRadnoVremeInput(
    datumOdSmena1: Date,
    datumDoSmena1: Date,
    datumOdSmena2: Date,
    datumDoSmena2: Date,
    datumOdSmena3: Date,
    datumDoSmena3: Date
) {
    var expandedTab by remember { mutableStateOf<Int?>(0) } // Initially, the first tab is expanded. Use null for no tab expanded.

    Column() {
        DropdownTab(
            title = "I Smena",
            expanded = expandedTab == 0,
            onTabClick = { expandedTab = if (expandedTab == 0) null else 0 }
        ) {
            TimeSelectionRow(1, datumOdSmena1, datumDoSmena1)
        }
        DropdownTab(
            title = "II Smena",
            expanded = expandedTab == 1,
            onTabClick = { expandedTab = if (expandedTab == 1) null else 1 }
        ) {
            TimeSelectionRow(2, datumOdSmena2, datumDoSmena2)
        }
        DropdownTab(
            title = "III Smena",
            expanded = expandedTab == 2,
            onTabClick = { expandedTab = if (expandedTab == 2) null else 2 }
        ) {
            TimeSelectionRow(3, datumOdSmena3, datumDoSmena3)
        }
    }
}

@Composable
fun DropdownTabsBrojRadnikaInput(
    brGradjRadnika: Int, brZanatlija: Int, brTehOsoblja: Int, brOstali: Int,
    brGradjRadnika1: Int, brZanatlija1: Int, brTehOsoblja1: Int, brOstali1: Int,
    brGradjRadnika2: Int, brZanatlija2: Int, brTehOsoblja2: Int, brOstali2: Int
) {
    var expandedTab by remember { mutableStateOf<Int?>(0) } // Initially, the first tab is expanded. Use null for no tab expanded.

    Column() {
        DropdownTab(
            title = "I Smena",
            expanded = expandedTab == 0,
            onTabClick = { expandedTab = if (expandedTab == 0) null else 0 }
        ) {
            NumberInputLayout(1, brGradjRadnika, brZanatlija, brTehOsoblja, brOstali)
        }
        DropdownTab(
            title = "II Smena",
            expanded = expandedTab == 1,
            onTabClick = { expandedTab = if (expandedTab == 1) null else 1 }
        ) {
            NumberInputLayout(2, brGradjRadnika1, brZanatlija1, brTehOsoblja1, brOstali1)
        }
        DropdownTab(
            title = "III Smena",
            expanded = expandedTab == 2,
            onTabClick = { expandedTab = if (expandedTab == 2) null else 2 }
        ) {
            NumberInputLayout(3, brGradjRadnika2, brZanatlija2, brTehOsoblja2, brOstali2)
        }
    }
}

@Composable
fun InfoFABWithDialog() {
    var showDialog by remember { mutableStateOf(false) }

    // Floating Action Button with Info Icon
    FloatingActionButton(
//        modifier = Modifier.padding(20.dp),
        onClick = { showDialog = true }
    ) {
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