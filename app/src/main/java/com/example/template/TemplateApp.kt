package com.example.template

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
import com.example.template.screens.EmptyComingSoon
import com.example.template.screens.HomeScreen
import com.example.template.utils.ContentType
import com.example.template.utils.DevicePosture
import com.example.template.utils.NavigationType
import com.example.template.utils.isBookPosture
import com.example.template.utils.isSeparating
import com.example.template.viewmodels.HomeUIState
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
    val selectedDestination = navBackStackEntry?.destination?.route ?: NavDestinations.INBOX

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
                viewModel = viewModel,
                navController = navController,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                homeUIState = homeUIState,
                contentType = contentType,
                onLoginSuccessful =
                {
                    navigateToTopLevelDestination(TOP_LEVEL_DESTINATIONS[0])
                    viewModel.logIn()
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
    viewModel: TemplateHomeViewModel,
    navController: NavHostController,
    navigateToTopLevelDestination: (TemplateTopLevelDestination) -> Unit,
    homeUIState: HomeUIState,
    contentType: ContentType,
    onLoginSuccessful: () -> Unit = {},
    selectedDestination: String,
    modifier: Modifier
) {
    val startDestination = if (!homeUIState.loggedIn) NavDestinations.LOGIN else selectedDestination

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavDestinations.LOGIN){
            LoginScreen(
                onLoginSuccessful = {
                    onLoginSuccessful()
                }
            )
        }
        composable(NavDestinations.INBOX) {
            HomeScreen(viewModel)
        }
        composable(NavDestinations.DM) {
            EmptyComingSoon()
//            if(contentType == ContentType.DUAL_PANE){
//                TemplateListAndDetailContent(
//                    homeUIState = homeUIState,
////                    modifier = Modifier.weight(1f)
//                )
//            } else {
//                TemplateListOnlyContent(
//                    homeUIState = homeUIState,
////                    modifier = Modifier.weight(1f)
//                )
//            }
        }
        composable(NavDestinations.ARTICLES) {
            EmptyComingSoon()
        }
        composable(NavDestinations.GROUPS) {
            EmptyComingSoon()
        }
    }
}










