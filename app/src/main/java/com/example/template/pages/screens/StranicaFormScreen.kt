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
import com.example.template.fakedata.TempRecord
import com.example.template.pages.elements.CustomTabRow
import com.example.template.pages.elements.DropdownTab
import com.example.template.pages.elements.FilePicker
import com.example.template.pages.elements.HorizontalLineSpacer
import com.example.template.pages.elements.LabeledRow
import com.example.template.pages.elements.LargeDescriptionTextField
import com.example.template.pages.elements.LargePrimedbeTextField
import com.example.template.pages.elements.MutedInputRow
import com.example.template.pages.elements.MyHeaderText
import com.example.template.pages.elements.NumberInputLayout
import com.example.template.pages.elements.SimpleTopAppBar
import com.example.template.pages.elements.Stampaj
import com.example.template.pages.elements.TimeSelectionRow
import com.example.template.pages.elements.TimeTemperatureRow
import com.example.template.pages.elements.MyInputRow
import com.example.template.pages.elements.showToast
import com.example.template.viewmodels.StraniceScreenViewModel
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StranicaFormScreen(
    viewModel: StraniceScreenViewModel,
) {
    val goToStrana: (Long) -> Unit = {
        viewModel.goToStrana(it)
    }
    val updateStranaByDate: (Date) -> Unit = {
        viewModel.goToStranaByDate(it)
    }
    val updateInfoOVremenu: (String, String, String) -> Unit = { it1, it2, it3 ->
        viewModel.updateInfoOVremenu(it1, it2, it3)
    }
    val onSmenaValueChange: (Int, Smena) -> Unit = { smenaRedniBroj, smena ->

    }
    val updateTempRecords: (TempRecord, TempRecord, TempRecord) -> Unit = { it1, it2, it3 ->

    }
    val updateOpisRada: (String) -> Unit = {

    }
    val updatePrimedbe: (String) -> Unit = {

    }
    val dodajPrilog: (String) -> Unit = {

    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    val strana = viewModel.uiState.collectAsState().value.strana
    println(strana)


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
                    dnevnikId = strana.dnevnikId,
                    stranicaId = strana.stranaId,
                    selectedTabIndex = selectedTabIndex,
                    changeSelectedIndex = { selectedTabIndex = it },
                    changeStranica = goToStrana,
                    updateStranaDate = updateStranaByDate,
                    onTextChange = updateInfoOVremenu,
                    onSmenaValueChange = onSmenaValueChange,
                    updateTempRecords = updateTempRecords,
                    updateOpisRada = updateOpisRada,
                    updatePrimedbe = updatePrimedbe,
                    dodajPrilog = dodajPrilog
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
    dnevnikId: Long,
    stranicaId: Long,
    selectedTabIndex: Int,
    changeSelectedIndex: (Int) -> Unit,
    changeStranica: (Long) -> Unit,
    updateStranaDate: (Date) -> Unit,
    onTextChange: (String, String, String) -> Unit,
    onSmenaValueChange: (Int, Smena) -> Unit,
    updateTempRecords: (TempRecord, TempRecord, TempRecord) -> Unit,
    updateOpisRada: (String) -> Unit,
    updatePrimedbe: (String) -> Unit,
    dodajPrilog: (String) -> Unit
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
                changeStranica(stranicaId.minus(1))
            },
            leftArrowEnabled = stranicaId > 1,
            onRightArrowClick = {
                changeStranica(stranicaId.plus(1))
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
                        strana,
                        onInputValueChange = onSmenaValueChange
                    )
                }
                1 -> {
                    SecondPage(
                        strana,
                        onTextChange,
                        updateTempRecords
                    )
                }
                2 -> {
                    ThirdPage(
                        strana,
                        updateOpisRada,
                        updatePrimedbe,
                        dodajPrilog
                    )
                }
            }
        }
    }
}


@Composable
fun FirstPage(
    strana: Strana,
    onInputValueChange: (Int, Smena) -> Unit
) {
    val smenaPrva = strana.smenaRadnici[0]
    val smenaDruga = strana.smenaRadnici[1]
    val smenaTreca = strana.smenaRadnici[2]
    MyHeaderText(text = "Radno Vreme")
    Spacer(modifier = Modifier.height(12.dp))
    DropdownTabsRadnoVremeInput(
        smenaPrva.datumOd, smenaPrva.datumDo,
        smenaDruga.datumOd, smenaDruga.datumDo,
        smenaTreca.datumOd, smenaTreca.datumDo,
        onInputValueChange
    )
    MyHeaderText(text = "Broj Radnika")
    Spacer(modifier = Modifier.height(12.dp))
    DropdownTabsBrojRadnikaInput(
        smenaPrva,
        smenaDruga,
        smenaTreca,
        onInputValueChange
    )
}

@Composable
fun SecondPage(strana: Strana, onTextChange: (String, String, String) -> Unit, updateTempRecords: (TempRecord, TempRecord, TempRecord) -> Unit) {
    DateAndDaySelector()
    HorizontalLineSpacer(modifier = Modifier.padding(top = 8.dp))
    TimeTemperatureRows(strana.smenaTemp, updateTempRecords)
    TripleInputRows(strana.sunacnoOblacnoKisa, strana.brzinaVetra, strana.nivoPodzemnihVoda, onTextChange)
}

@Composable
fun ThirdPage(
    strana: Strana,
    updateOpisRada: (String) -> Unit,
    updatePrimedbe: (String) -> Unit,
    dodajPrilog: (String) -> Unit
) {
    LargeDescriptionTextField(strana.opisRada, updateOpisRada)
    Spacer(modifier = Modifier.padding(4.dp))
    LargePrimedbeTextField(strana.primedbe, updatePrimedbe)
    FilePicker(dodajPrilog)
    MyHeaderText(text = "Informacije")
    LabeledRow(label = "Vode Dnevnik", value = strana.vodeDnevnik.joinToString(", "))
    // TODO Dugme
    LabeledRow(label = "Izvodjac Radova", value = "Potpis")
    LabeledRow(label = "Nadzorni Organ", value = strana.nadzorniOrgan)

    Spacer(modifier = Modifier.height(16.dp))
    Row {
        Column(Modifier.weight(1f)) {
            Stampaj()
        }
    }
}

@Composable
fun TimeTemperatureRows(smenaTemp: Array<TempRecord>, updateTempRecords: (TempRecord, TempRecord, TempRecord) -> Unit) {
    var temp1 by remember { mutableStateOf(smenaTemp[0]) }
    var temp2 by remember { mutableStateOf(smenaTemp[1]) }
    var temp3 by remember { mutableStateOf(smenaTemp[2]) }

    LaunchedEffect(temp1, temp2, temp3) {
        // This will be called every time text1, text2, or text3 changes
        updateTempRecords(temp1, temp2, temp3)
    }

    TimeTemperatureRow(smenaTemp[0]) { tempRecod ->
        temp1 = tempRecod
    }
    TimeTemperatureRow(smenaTemp[1]) { tempRecod ->
        temp2 = tempRecod
    }
    TimeTemperatureRow(smenaTemp[2]) { tempRecod ->
        temp3 = tempRecod
    }
}

@Composable
fun TripleInputRows(t1: String, t2: String, t3: String, onTextChange: (String, String, String) -> Unit) {
    var text1 by remember { mutableStateOf(t1) }
    var text2 by remember { mutableStateOf(t2) }
    var text3 by remember { mutableStateOf(t3) }

    LaunchedEffect(text1, text2, text3) {
        // This will be called every time text1, text2, or text3 changes
        onTextChange(text1, text2, text3)
    }

    MyInputRow(text = text1) {
        text1 = it
    }
    MyInputRow(text = text2) {
        text2 = it
    }
    MyInputRow(text = text3) {
        text3 = it
    }
}

@Composable
fun DateAndDaySelector() {
    MutedInputRow(text = "Ponedeljak")
    Spacer(modifier = Modifier.height(16.dp))

    // Imam sad datum u toolbaru
//    var showDialog by remember { mutableStateOf(false) }
//    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
//    val context = LocalContext.current
//    DateChooser(
//        context = context,
//        showDialog = showDialog,
//        onShowDialogChange = { showDialog = it },
//        selectedDate = selectedDate,
//        onDateSelected = { newDate ->
//            selectedDate = newDate
//        }
//    )
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
    datumDoSmena3: Date,
    onInputValueChange: (Int, Smena) -> Unit
) {
    var expandedTab by remember { mutableStateOf<Int?>(0) } // Initially, the first tab is expanded. Use null for no tab expanded.

    Column() {
        DropdownTab(
            title = "I Smena",
            expanded = expandedTab == 0,
            onTabClick = { expandedTab = if (expandedTab == 0) null else 0 }
        ) {
            TimeSelectionRow(1, datumOdSmena1, datumDoSmena1, onInputValueChange)
        }
        DropdownTab(
            title = "II Smena",
            expanded = expandedTab == 1,
            onTabClick = { expandedTab = if (expandedTab == 1) null else 1 }
        ) {
            TimeSelectionRow(2, datumOdSmena2, datumDoSmena2, onInputValueChange)
        }
        DropdownTab(
            title = "III Smena",
            expanded = expandedTab == 2,
            onTabClick = { expandedTab = if (expandedTab == 2) null else 2 }
        ) {
            TimeSelectionRow(3, datumOdSmena3, datumDoSmena3, onInputValueChange)
        }
    }
}

@Composable
fun DropdownTabsBrojRadnikaInput(
    smenaPrva: Smena,
    smenaDruga: Smena,
    smenaTreca: Smena,
    onInputValueChange: (Int, Smena) -> Unit
) {
    var expandedTab by remember { mutableStateOf<Int?>(0) } // Initially, the first tab is expanded. Use null for no tab expanded.

    Column() {
        DropdownTab(
            title = "I Smena",
            expanded = expandedTab == 0,
            onTabClick = { expandedTab = if (expandedTab == 0) null else 0 }
        ) {
            NumberInputLayout(1, smenaPrva, onInputValueChange)
        }
        DropdownTab(
            title = "II Smena",
            expanded = expandedTab == 1,
            onTabClick = { expandedTab = if (expandedTab == 1) null else 1 }
        ) {
            NumberInputLayout(2, smenaDruga, onInputValueChange)
        }
        DropdownTab(
            title = "III Smena",
            expanded = expandedTab == 2,
            onTabClick = { expandedTab = if (expandedTab == 2) null else 2 }
        ) {
            NumberInputLayout(3, smenaTreca, onInputValueChange)
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