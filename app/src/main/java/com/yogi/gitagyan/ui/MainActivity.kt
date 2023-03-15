package com.yogi.gitagyan.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.Modifier
import com.yogi.gitagyan.ui.theme.Background
import com.yogi.gitagyan.ui.viewmodels.GitaGyanViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.google.accompanist.adaptive.calculateDisplayFeatures

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


     val viewModel: GitaGyanViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel = ViewModelProvider(this)[GitaGyanViewModel::class.java]
        setContent {
            val windowSize = calculateWindowSizeClass(activity = this)
            val displayFeatures = calculateDisplayFeatures(activity = this)


            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Background
            ) {
                GitaGyanApp(
                    windowSize = windowSize,
                    displayFeatures = displayFeatures,
                    viewModel = viewModel
                )
            }
        }

    }
}

