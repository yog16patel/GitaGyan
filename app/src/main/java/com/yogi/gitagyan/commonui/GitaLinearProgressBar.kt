package com.yogi.gitagyan.commonui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.yogi.gitagyan.ui.theme.Saffron

@Composable
fun GitaLinearProgressBar(
    progressbarState: GitaProgressbarState = GitaProgressbarState()
) {
    var progress by remember {
        mutableStateOf(0.0f)
    }
    val progressAnimationDuration = 500
    progress = progressbarState.currentItem/progressbarState.totalItem
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = progressAnimationDuration, easing = FastOutSlowInEasing)
    )

    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        progress = progressAnimation,
        color = Saffron
    )
}