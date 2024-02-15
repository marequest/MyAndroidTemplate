package com.example.template.pages.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.template.fakedata.DnevniciDataProvider
import com.example.template.fakedata.Dnevnik

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DnevniciScreen(onDnevnikClick: (Int) -> Unit = {}) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val scrollState = rememberScrollState()


    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current



    Scaffold(
        topBar = {
            Column {
                SimpleTopAppBar(scrollBehavior = scrollBehavior, text = "Dnevnici")
            }
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(0.dp)) {}
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            for (dnevnik in DnevniciDataProvider.allDnevnici) {
                DenvnikCard(dnevnik, onDnevnikClick = onDnevnikClick)
            }

        }
    }
}
@Composable
fun DenvnikCard(dnevnik: Dnevnik, onDnevnikClick: (Int) -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .shadow(8.dp, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clickable { onDnevnikClick },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "#" + dnevnik.id.toString() + " " + dnevnik.projekat,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = dnevnik.grupaRadova,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))

            DetailRow(header = "Datum otvaranja:", content = dnevnik.datumOtvaranja)
            DetailRow(header = "Izvodjac radova:", content = dnevnik.izvodjacRadova)
            DetailRow(header = "Nadzorni organ:", content = dnevnik.nadzorniOrgan)
        }
    }
}

@Composable
fun DetailRow(header: String, content: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 4.dp)
    ) {
        Text(
            text = header,
            modifier = Modifier.weight(0.65f),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = content,
            modifier = Modifier.weight(1.45f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
