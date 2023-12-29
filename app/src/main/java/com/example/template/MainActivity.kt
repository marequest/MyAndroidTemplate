package com.example.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.example.template.data.local.LocalEmailsDataProvider
import com.example.template.ui.theme.TemplateTheme
import com.example.template.utils.DevicePosture
import com.example.template.utils.isBookPosture
import com.example.template.utils.isSeparating
import com.google.accompanist.adaptive.calculateDisplayFeatures
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class MainActivity : ComponentActivity() {

    private val viewModel: TemplateHomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            TemplateTheme {
                val windowSize = calculateWindowSizeClass(activity = this)
                val uiState = viewModel.uiState.collectAsState().value
                val displayFeatures = calculateDisplayFeatures(this)


                TemplateApp(
                    homeUIState = uiState,
                    windowSize = windowSize,
                    displayFeatures = displayFeatures
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun TemplateAppPreview() {
    TemplateTheme {
        TemplateApp(
            homeUIState = HomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 700, heightDp = 500)
@Composable
fun TemplateAppPreviewTablet() {
    TemplateTheme {
        TemplateApp(
            homeUIState = HomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowSizeClass.calculateFromSize(DpSize(700.dp, 500.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 500, heightDp = 700)
@Composable
fun TemplateAppPreviewPortrait() {
    TemplateTheme {
        TemplateApp(
            homeUIState = HomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowSizeClass.calculateFromSize(DpSize(500.dp, 700.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 1100, heightDp = 600)
@Composable
fun TemplateAppPreviewDesktop() {
    TemplateTheme {
        TemplateApp(
            homeUIState = HomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowSizeClass.calculateFromSize(DpSize(1100.dp, 600.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 600, heightDp = 1100)
@Composable
fun TemplateAppPreviewDesktopPortrait() {
    TemplateTheme {
        TemplateApp(
            homeUIState = HomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowSizeClass.calculateFromSize(DpSize(600.dp, 1100.dp)),
            displayFeatures = emptyList(),
        )
    }
}