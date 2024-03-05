package com.example.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.template.ui.theme.TemplateTheme
import com.example.template.viewmodels.DnevniciListScreenViewModel
import com.example.template.viewmodels.StraniceScreenViewModel
import com.example.template.viewmodels.LoginViewModel
import com.example.template.viewmodels.ProfileScreenViewModel
import com.example.template.viewmodels.ProjectScreenViewModel
import com.google.accompanist.adaptive.calculateDisplayFeatures
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val dnevnikViewModel: StraniceScreenViewModel by viewModels()
    private val dnevniciListScreenViewModel: DnevniciListScreenViewModel by viewModels()
    private val projectScreenViewModel: ProjectScreenViewModel by viewModels()
    private val profileScreenViewModel: ProfileScreenViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)


        setContent {
            TemplateTheme {
                val windowSize = calculateWindowSizeClass(activity = this)
                val displayFeatures = calculateDisplayFeatures(this)

                TemplateApp(
                    loginViewModel = loginViewModel,
                    dnevnikViewModel = dnevnikViewModel,
                    dnevniciListScreenViewModel = dnevniciListScreenViewModel,
                    projectScreenViewModel = projectScreenViewModel,
                    profileScreenViewModel = profileScreenViewModel,
                    windowSize = windowSize,
                    displayFeatures = displayFeatures
                )
            }
        }
    }
}



//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Preview(showBackground = true)
//@Composable
//fun TemplateAppPreview() {
//    TemplateTheme {
//        TemplateApp(
//            viewModel = TemplateHomeViewModel(),
//            homeUIState = HomeUIState(),
//            windowSize = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp)),
//            displayFeatures = emptyList(),
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Preview(showBackground = true, widthDp = 700, heightDp = 500)
//@Composable
//fun TemplateAppPreviewTablet() {
//    TemplateTheme {
//        TemplateApp(
//            viewModel = TemplateHomeViewModel(),
//            homeUIState = HomeUIState(),
//            windowSize = WindowSizeClass.calculateFromSize(DpSize(700.dp, 500.dp)),
//            displayFeatures = emptyList(),
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Preview(showBackground = true, widthDp = 500, heightDp = 700)
//@Composable
//fun TemplateAppPreviewPortrait() {
//    TemplateTheme {
//        TemplateApp(
//            viewModel = TemplateHomeViewModel(),
//            homeUIState = HomeUIState(),
//            windowSize = WindowSizeClass.calculateFromSize(DpSize(500.dp, 700.dp)),
//            displayFeatures = emptyList(),
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Preview(showBackground = true, widthDp = 1100, heightDp = 600)
//@Composable
//fun TemplateAppPreviewDesktop() {
//    TemplateTheme {
//        TemplateApp(
//            viewModel = TemplateHomeViewModel(),
//            homeUIState = HomeUIState(),
//            windowSize = WindowSizeClass.calculateFromSize(DpSize(1100.dp, 600.dp)),
//            displayFeatures = emptyList(),
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Preview(showBackground = true, widthDp = 600, heightDp = 1100)
//@Composable
//fun TemplateAppPreviewDesktopPortrait() {
//    TemplateTheme {
//        TemplateApp(
//            viewModel = TemplateHomeViewModel(),
//            homeUIState = HomeUIState(),
//            windowSize = WindowSizeClass.calculateFromSize(DpSize(600.dp, 1100.dp)),
//            displayFeatures = emptyList(),
//        )
//    }
//}