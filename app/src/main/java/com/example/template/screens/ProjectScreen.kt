package com.example.template.screens

import SimpleTopAppBar
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.template.screens.elements.DownloadPDF
import com.example.template.screens.elements.LabeledRow
import com.example.template.screens.elements.MyHeaderText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen() {
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(vertical = 16.dp),
                title = { Text(
                    text = "Projekat: Izgradnja i rekonstrukcija poletno sletnih staza",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W800,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Toast.makeText(context, "Sacuvano!", Toast.LENGTH_LONG).show()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Download, "Download")
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(0.dp)
            ) {
            }
        }
//        bottomBar = {
//            BottomAppBar(
//                containerColor = Color.Gray,
//                actions = {
//                    IconButton(
//                        modifier = Modifier.padding(start = 20.dp),
//                        onClick = {
//                            Toast.makeText(context, "Neka akcija", Toast.LENGTH_SHORT).show()
//                        }
//                    ) {
//                        Icon(Icons.Filled.Add, contentDescription = "Localized description")
//                    }
//                },
//                floatingActionButton = {
//                    FloatingActionButton(
//                        onClick = {
//                            Toast.makeText(context, "Sacuvano!", Toast.LENGTH_LONG).show()
//                        },
//                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
//                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
//                        ) {
//                        Icon(Icons.Filled.Download, "Download")
//                    }
//                }
//            )
//        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .padding(innerPadding)
        ) {

            LabeledRow(label = "Dnevnik", value = "Izgradnja piste aerodroma")
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

