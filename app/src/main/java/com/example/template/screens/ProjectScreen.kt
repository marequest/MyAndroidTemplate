package com.example.template.screens

import SimpleTopAppBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.template.screens.elements.LabeledRow
import com.example.template.screens.elements.MyHeaderText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen() {
    val scrollState = rememberScrollState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = { SimpleTopAppBar(scrollBehavior = scrollBehavior, text = "Projekat: Izgradnja i rekonstrukcija poletno sletnih staza") }
    ) { innerPadding ->
        Column(modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(16.dp)
            .padding(innerPadding)
        ) {
            MyHeaderText(text = "Dnevnik Vode")
            LabeledRow(label = "Odgovorni Izvodjac", value = "Petar Nikolic")
            LabeledRow(label = "Vrsi Nadzor", value = "Sava Mihajlovic")

            MyHeaderText(text = "Pomoc U Vodjenju Dnevnika")

            MyHeaderText(text = "Imaju Pristup")
            LabeledRow(label = "Ima Pristup", value = "Mirko Radosavljevic")

            MyHeaderText(text = "Pravna Lica")
            LabeledRow(label = "Izvodjac", value = "Serbiaprojekt inzenjering doo")
            LabeledRow(label = "Vrsi Nadzor", value = "Tehnoprojekt kopring a.d.")
            LabeledRow(label = "Investitor", value = "AD Aerodrom Nikola Tesla Beograd")

            MyHeaderText(text = "Lokacija")
            LabeledRow(label = "Mesto", value = "Surcin")
            LabeledRow(label = "Zemlja", value = "Srbija")

            MyHeaderText(text = "Datumi")
            LabeledRow(label = "Datum Pocetka Radova", value = "21.08.2023.")
            LabeledRow(label = "Datum Zavrsetka Radova", value = "10.12.2023.")

            MyHeaderText(text = "Informacije")
            LabeledRow(label = "Broj Licence Odgovornog Izvodjaca", value = "410I90214 (N)")
            LabeledRow(label = "Broj Licence Nadzornog Oragana", value = "410I90214 (N)")
            LabeledRow(label = "Broj Gradjevinske Dozvole", value = "GD2301341-12/10")
            LabeledRow(label = "Broj Ugovora", value = "127/21")

            MyHeaderText(text = "Dokumenti")
            LabeledRow(label = "Resenje O Imenovanju Odgovornog Izvodjaca", value = "Link")
            LabeledRow(label = "Resenje O Imenovanju Nadzornog Organa", value = "Link")

        }
    }
}

