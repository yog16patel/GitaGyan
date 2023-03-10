package com.yogi.gitagyan.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yogi.gitagyan.GitaGyanAppState
import com.yogi.gitagyan.Screen
import com.yogi.gitagyan.commonui.GitaCenterAlignedTopAppBar
import com.yogi.gitagyan.commonui.GitaTopAppBar
import com.yogi.gitagyan.rememberGitaGyanAppState
import com.yogi.gitagyan.ui.appbar.AppbarState
import com.yogi.gitagyan.ui.chapterlist.ChapterList
import com.yogi.gitagyan.ui.slokadetails.SlokaDetails
import com.yogi.gitagyan.ui.viewmodels.GitaGyanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitaGyanApp(
    appState: GitaGyanAppState = rememberGitaGyanAppState(),
    viewModel: GitaGyanViewModel
) {
    val navController = appState.navController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var appBarState by remember {
        mutableStateOf(AppbarState())
    }

    var showCenterAppBar by remember {
        mutableStateOf(false)
    }
    showCenterAppBar = navBackStackEntry?.destination?.route != Screen.ChapterList.route
    Scaffold(
        topBar = {
            when (showCenterAppBar) {
                true -> GitaCenterAlignedTopAppBar(appBarState = appBarState, appState = appState)
                else -> GitaTopAppBar(appBarState = appBarState)
            }
        }
    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = appState.navController,
                startDestination = Screen.ChapterList.route
            ) {
                composable(Screen.ChapterList.route) { backStackEntry ->
                    ChapterList(
                        viewModel = viewModel,
                        navigateToSlokaComposable = { chapterNumber ->
                            appState.navigateToChapterDetails(
                                chapterNumber.toString(),
                                backStackEntry
                            )
                        },
                        onComposing = {
                            appBarState = it
                        }
                    )
                }
                composable(Screen.ChapterDetails.route) {
                    SlokaDetails(
                        viewModel = viewModel,
                        onComposing = {
                            appBarState = it
                        }
                    )
                }
            }
        }
    }
}