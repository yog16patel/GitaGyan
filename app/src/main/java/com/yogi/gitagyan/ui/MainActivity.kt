package com.yogi.gitagyan.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.Modifier
import com.yogi.gitagyan.ui.theme.Background
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.yogi.gitagyan.ui.musicplayer.MediaPlayerService
import com.yogi.gitagyan.ui.musicplayer.rememberBoundLocalService

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel = ViewModelProvider(this)[GitaGyanViewModel::class.java]
        setContent {
            val windowSize = calculateWindowSizeClass(activity = this)
            val displayFeatures = calculateDisplayFeatures(activity = this)
            val musicPlayerService = rememberBoundLocalService<MediaPlayerService, MediaPlayerService.LocalBinder>{ service }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Background
            ) {
                GitaGyanApp(
                    windowSize = windowSize,
                    displayFeatures = displayFeatures,
                    musicPlayerService = musicPlayerService
                )
            }
        }

    }
}

