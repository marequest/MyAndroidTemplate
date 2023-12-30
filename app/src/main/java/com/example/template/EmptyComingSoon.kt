package com.example.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.network.network.CatFact
import com.example.template.viewmodels.TemplateHomeViewModel

@Composable
fun EmptyComingSoon(
    viewModel: TemplateHomeViewModel,
    modifier: Modifier = Modifier
) {
    val catState by viewModel.catState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.fetchCatFacts()
    }
    
//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            modifier = Modifier.padding(8.dp).height(30.dp),
//            text = stringResource(id = R.string.empty_screen_title),
//            style = MaterialTheme.typography.titleLarge,
//            textAlign = TextAlign.Center,
//            color = MaterialTheme.colorScheme.primary,
//        )
//        Text(
//            modifier = Modifier.padding(horizontal = 8.dp).height(30.dp),
//            text = stringResource(id = R.string.empty_screen_subtitle),
//            style = MaterialTheme.typography.bodySmall,
//            textAlign = TextAlign.Center,
//            color = MaterialTheme.colorScheme.outline
//        )
//        CatFactsList(catState.cats)
//
//    }

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
                CatFactsList(catState.cats)
            }

            // Display error message if there is an error
            catState.error?.let { error ->
                Text(text = "Error: $error", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun CatFactsList(catFacts: List<CatFact>) {
    LazyColumn {
        itemsIndexed(catFacts) { index, catFact ->
            CatFactItem(catFact = catFact)
        }
    }
}

@Composable
fun CatFactItem(catFact: CatFact) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        // Your UI for displaying a single cat fact
        // For example:
        Text(text = catFact.text, style = MaterialTheme.typography.titleMedium)
    }
}



@Preview
@Composable
fun ComingSoonPreview() {
    EmptyComingSoon(TemplateHomeViewModel())
}
