package com.yogi.gitagyan.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.window.layout.DisplayFeature
import com.yogi.gitagyan.GitaGyanAppState
import com.yogi.gitagyan.GitaGyanNavGraph

import com.yogi.gitagyan.rememberGitaGyanAppState
import com.yogi.gitagyan.ui.musicplayer.MediaPlayerService

import com.yogi.gitagyan.ui.util.GitaContentType

@Composable
fun GitaGyanApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    appState: GitaGyanAppState = rememberGitaGyanAppState(),
    musicPlayerService: MediaPlayerService?
) {

    val contentType: GitaContentType = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> GitaContentType.SINGLE_PANE
        else -> {
            //WindowWidthSizeClass.Expanded
            GitaContentType.DUAL_PANE
        }
    }

    GitaGyanNavGraph(
        modifier = Modifier,
        contentType = contentType,
        displayFeatures = displayFeatures,
        appState = appState,
        musicPlayerService = musicPlayerService
    )

}