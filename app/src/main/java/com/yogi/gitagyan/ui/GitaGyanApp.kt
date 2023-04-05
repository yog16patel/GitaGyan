package com.yogi.gitagyan.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import com.yogi.gitagyan.GitaGyanAppState
import com.yogi.gitagyan.Screen
import com.yogi.gitagyan.rememberGitaGyanAppState
import com.yogi.gitagyan.ui.chapterlist.ChapterListScreen
import com.yogi.gitagyan.ui.slokadetails.ChapterOverviewScreen
import com.yogi.gitagyan.ui.userhome.UserHomeScreen
import com.yogi.gitagyan.ui.util.GitaContentType
import com.yogi.gitagyan.ui.viewmodels.GitaGyanViewModel

@Composable
fun GitaGyanApp(
    windowSize : WindowSizeClass ,
    displayFeatures: List<DisplayFeature> ,
    appState: GitaGyanAppState = rememberGitaGyanAppState(),
    viewModel: GitaGyanViewModel
) {

    val contentType: GitaContentType =  when(windowSize.widthSizeClass){
        WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium  -> GitaContentType.SINGLE_PANE
        else -> {
            //WindowWidthSizeClass.Expanded
            GitaContentType.DUAL_PANE
        }
    }

    NavHost(
        navController = appState.navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) { backStackEntry ->
            SplashScreen {
                appState.navigateToUserHome(backStackEntry) {
                    popUpTo(Screen.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(Screen.UserHomeScreen.route) { backStackEntry ->
            UserHomeScreen(
                viewModel = viewModel,
                navigateToChapterList = {
                    appState.navigateToChapterList(backStackEntry)
                },
                continueReading = {
                    appState.navigateChapterOverview(backStackEntry)
                }
            )
        }
        composable(Screen.ChapterList.route) { backStackEntry ->
            ChapterListScreen(
                viewModel = viewModel,
                navigateToNext = {
                    appState.navigateChapterOverview(backStackEntry)
                },
                onBackButtonPressed = {
                    appState.navController.popBackStack()
                }
            )
        }
        composable(Screen.ChapterOverview.route) { backStackEntry ->
            ChapterOverviewScreen(
                viewModel = viewModel,
                contentType = contentType,
                displayFeatures = displayFeatures,
                goBack = {
                    appState.navController.popBackStack()
                },
                closeDetailScreen = {
                    viewModel.closeDetailScreen()
                },
                navigateToDetail = { index, contentType ->
                    viewModel.setLastSelectedSloka(index, contentType, index)
                }
            )
        }
    }


}