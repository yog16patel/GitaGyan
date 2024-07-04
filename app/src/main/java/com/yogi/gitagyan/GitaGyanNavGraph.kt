package com.yogi.gitagyan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import com.yogi.gitagyan.ui.SplashScreen
import com.yogi.gitagyan.ui.chapterlist.ChapterListScreen
import com.yogi.gitagyan.ui.musicplayer.PlayerContentRegular
import com.yogi.gitagyan.ui.slokadetails.ChapterOverviewScreen
import com.yogi.gitagyan.ui.userhome.UserHomeScreen
import androidx.navigation.compose.NavHost
import androidx.window.layout.DisplayFeature
import com.yogi.gitagyan.ui.musicplayer.MediaPlayerService
import com.yogi.gitagyan.ui.userhome.UserHomeScreenViewModel
import com.yogi.gitagyan.ui.util.GitaContentType
import com.yogi.gitagyan.ui.slokadetails.GitaGyanViewModel

@Composable
fun GitaGyanNavGraph(
    modifier: Modifier = Modifier,
    contentType: GitaContentType,
    displayFeatures: List<DisplayFeature>,
    appState: GitaGyanAppState = rememberGitaGyanAppState(),
    musicPlayerService: MediaPlayerService?
) {

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
            val viewModel: UserHomeScreenViewModel = hiltViewModel()
            UserHomeScreen(
                viewModel = viewModel,
                navigateToChapterList = {
                    appState.navigateToChapterList(backStackEntry)
                },
                continueReading = { chNumber, slockNumber ->
                    appState.navigateChapterOverview(backStackEntry, chNumber, slockNumber)
                },
                onMusicClicked = {
                    appState.navigateMusicPlayer(backStackEntry)
                }
            )
        }
        composable(Screen.ChapterList.route) { backStackEntry ->
            val viewModel: GitaGyanViewModel = hiltViewModel()
            ChapterListScreen(
                viewModel = viewModel,
                navigateToNext = { chNumber, slockNumber ->
                    appState.navigateChapterOverview(backStackEntry, chNumber, slockNumber)
                },
                onBackButtonPressed = {
                    appState.navController.popBackStack()
                }
            )
        }
        composable(Screen.ChapterOverview.withNavItems) { backStackEntry ->
            val viewModel: GitaGyanViewModel = hiltViewModel()
            val chNumber = backStackEntry.arguments?.getString("chNumber")?: "1"
            val slockNumber = backStackEntry.arguments?.getString("slockNumber") ?: "1"
            LaunchedEffect(viewModel) {
                viewModel.continueReading(chNumber.toInt(), slockNumber.toInt())
            }
            ChapterOverviewScreen(
                viewModel = viewModel,
                contentType = contentType,
                displayFeatures = displayFeatures,
                selectedChapter = chNumber.toInt(),
                goBack = {
                    appState.navController.popBackStack()
                },
                closeDetailScreen = {
                    viewModel.closeDetailScreen()
                },
                navigateToDetail = { index, contentType ->
                    viewModel.setLastSelectedSloka(index, contentType, )
                }
            )
        }
        composable(Screen.MusicPlayer.route) {
            PlayerContentRegular(
                musicPlayerService = musicPlayerService
            )
        }
    }
}