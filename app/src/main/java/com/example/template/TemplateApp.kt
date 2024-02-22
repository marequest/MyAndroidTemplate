package com.example.template

import com.example.template.pages.screens.DnevnikFormScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.example.template.navigation.NavDestinations
import com.example.template.navigation.NavigationDrawerContent
import com.example.template.navigation.TOP_LEVEL_DESTINATIONS
import com.example.template.navigation.TemplateBottomNavigationBar
import com.example.template.navigation.TemplateNavigationActions
import com.example.template.navigation.TemplateNavigationRail
import com.example.template.navigation.TemplateTopLevelDestination
import com.example.template.pages.screens.LoginScreen
import com.example.template.pages.screens.ProfileScreen
import com.example.template.pages.screens.DnevniciListScreen
import com.example.template.pages.screens.DnevnikSettingsScreen
import com.example.template.pages.screens.RegistrationScreen
import com.example.template.ui.utils.ContentType
import com.example.template.ui.utils.DevicePosture
import com.example.template.ui.utils.NavigationType
import com.example.template.ui.utils.isBookPosture
import com.example.template.ui.utils.isSeparating
import com.example.template.viewmodels.DnevniciListScreenViewModel
import com.example.template.viewmodels.DnevnikScreenViewModel
import com.example.template.viewmodels.HomeUIState
import com.example.template.viewmodels.ProfileScreenViewModel
import com.example.template.viewmodels.ProjectScreenViewModel
import com.example.template.viewmodels.TemplateHomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateApp(
    viewModel: TemplateHomeViewModel,
    homeUIState: HomeUIState,
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>
) {

    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }


    val navigationType: NavigationType
    val contentType: ContentType

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.SINGLE_PANE

        }
        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture is DevicePosture.BookPosture
                || foldingDevicePosture is DevicePosture.Separating) {
                ContentType.DUAL_PANE
            } else {
                ContentType.SINGLE_PANE
            }
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                NavigationType.NAVIGATION_RAIL
            } else {
                NavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = ContentType.DUAL_PANE
        }
        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.SINGLE_PANE
        }
    }

    TemplateNavigationWrapperUI(viewModel, navigationType, contentType, homeUIState)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TemplateNavigationWrapperUI(
    viewModel: TemplateHomeViewModel,
    navigationType: NavigationType,
    contentType: ContentType,
    homeUIState: HomeUIState
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        TemplateNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: NavDestinations.DNEVNICI_LIST_SCREEN

    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER && homeUIState.loggedIn) {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet {
                    NavigationDrawerContent(
                        selectedDestination,
                        navigateToTopLevelDestination = navigationActions::navigateTo
                    )
                }
            }
        ) {
            TemplateAppContent(
                viewModel = viewModel,
                navController = navController,
                navigationType = navigationType,
                selectedDestination = selectedDestination,
                navigationActions = navigationActions,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                contentType = contentType,
                homeUIState = homeUIState
            )
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    NavigationDrawerContent(
                        selectedDestination,
                        navigateToTopLevelDestination = navigationActions::navigateTo,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                }
            },
            drawerState = drawerState
        ) {
            TemplateAppContent(
                viewModel = viewModel,
                navController = navController,
                navigationType = navigationType,
                selectedDestination = selectedDestination,
                navigationActions = navigationActions,
                navigateToTopLevelDestination = navigationActions::navigateTo,
                contentType = contentType,
                homeUIState = homeUIState,
                onDrawerClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }
}


@Composable
fun TemplateAppContent(
    viewModel: TemplateHomeViewModel,
    navController: NavHostController,
    navigationType: NavigationType,
    selectedDestination: String,
    navigationActions: TemplateNavigationActions,
    navigateToTopLevelDestination: (TemplateTopLevelDestination) -> Unit,
    contentType: ContentType,
    homeUIState: HomeUIState,
    onDrawerClicked: () -> Unit = {}
) {

    Row(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = (navigationType == NavigationType.NAVIGATION_RAIL && homeUIState.loggedIn)) {
            TemplateNavigationRail(
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                onDrawerClicked = onDrawerClicked
            )
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            TemplateNavHost(
                templateViewModel = viewModel,
                navController = navController,
                navigationActions = navigationActions,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                homeUIState = homeUIState,
                contentType = contentType,
                onLoginSuccessful =
                {
                    navigateToTopLevelDestination(TOP_LEVEL_DESTINATIONS[0])
                    viewModel.logIn()
                },
                onRegistrationSuccessful = {

                },
                selectedDestination = selectedDestination,
                modifier = Modifier.weight(1f)
            )

            AnimatedVisibility(visible = (navigationType == NavigationType.BOTTOM_NAVIGATION && homeUIState.loggedIn)) {
                TemplateBottomNavigationBar(
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination
                )
            }
        }
    }
}

@Composable
fun TemplateNavHost(
    templateViewModel: TemplateHomeViewModel,
    navController: NavHostController,
    navigationActions: TemplateNavigationActions,
    navigateToTopLevelDestination: (TemplateTopLevelDestination) -> Unit,
    homeUIState: HomeUIState,
    contentType: ContentType,
    onLoginSuccessful: () -> Unit = {},
    onRegistrationSuccessful: () -> Unit = {},
    selectedDestination: String,
    modifier: Modifier
) {
    val startDestination = if (!homeUIState.loggedIn) NavDestinations.LOGIN else selectedDestination

    val dnevnikViewModel: DnevnikScreenViewModel = viewModel()
    val dnevniciListScreenViewModel: DnevniciListScreenViewModel = viewModel()
    val projectScreenViewModel: ProjectScreenViewModel = viewModel()
    val profileScreenViewModel: ProfileScreenViewModel = viewModel(factory = ProfileScreenViewModel.Factory)

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavDestinations.LOGIN){
            LoginScreen(
                onLoginSuccessful = {
                    onLoginSuccessful()
                },
                goToRegistrationScreen = {
                    navigationActions.navigateTo(NavDestinations.REGISTRATION_SCREEN)
                }
            )
        }
        composable(NavDestinations.REGISTRATION_SCREEN){
            RegistrationScreen(
                onRegistrationSuccessful = {
                    onRegistrationSuccessful()
                },
                goToLoginScreen = {
                    navigationActions.navigateTo(NavDestinations.LOGIN)
                }
            )
        }

        composable(NavDestinations.DNEVNICI_LIST_SCREEN) {
            DnevniciListScreen(
                onDnevnikClick = {dnevnikId ->
                    dnevnikViewModel.setDnevnikId(dnevnikId.toString())
                    navigationActions.navigateTo(NavDestinations.DNEVNIK_FORM_SCREEN)
                },
                onDnevnikSettingsClick = {dnevnikId ->
                    dnevnikViewModel.setDnevnikId(dnevnikId.toString())
                    navigationActions.navigateTo(NavDestinations.DNEVNIK_SETTINGS_SCREEN)
                }
            )
        }
        composable(NavDestinations.DNEVNIK_SETTINGS_SCREEN) {
            DnevnikSettingsScreen(dnevnikViewModel.uiState.collectAsState().value.dnevnikId)
        }
        composable(NavDestinations.DNEVNIK_FORM_SCREEN) {
            DnevnikFormScreen(dnevnikViewModel.uiState.collectAsState().value.dnevnikId)
        }
        composable(NavDestinations.PROFILE_SCREEN) {
            ProfileScreen()
        }
    }
}










