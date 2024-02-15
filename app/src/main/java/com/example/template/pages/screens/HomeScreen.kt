package com.example.template.pages.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.network.network.CatFact
import com.example.template.viewmodels.TemplateHomeViewModel

@Composable
fun HomeScreen(
    viewModel: TemplateHomeViewModel,
    modifier: Modifier = Modifier
) {
    val catState by viewModel.catState.collectAsState()


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (catState.isLoading) {
                // Loading indicator
                CircularProgressIndicator(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentWidth(align = Alignment.CenterHorizontally))
            } else {
                // Display cat facts
                HomeScreenList(catState.cats)
            }

            // Display error message if there is an error
            catState.error?.let { error ->
                Text(text = "Error: $error", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun HomeScreenList(catFacts: List<CatFact>) {
    LazyColumn {
        itemsIndexed(catFacts) { index, catFact ->
            HomeScreenItem(catFact = catFact)
        }
    }
}

@Composable
fun HomeScreenItem(catFact: CatFact) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {

        Text(text = catFact.text, style = MaterialTheme.typography.titleMedium)
    }
}



@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(TemplateHomeViewModel())
}