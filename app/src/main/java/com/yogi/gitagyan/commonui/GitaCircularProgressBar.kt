package com.yogi.gitagyan.commonui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yogi.gitagyan.R
import com.yogi.gitagyan.ui.theme.Background
import com.yogi.gitagyan.ui.theme.Saffron

@Composable
fun GitaCircularProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(80.dp, 80.dp),
            color = Saffron
        )
    }
}

@Composable
fun ImageBorderAnimation() {
    val initialTransition = rememberInfiniteTransition()
    val rotateAnimation = initialTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing))
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_om_symbol),
            contentDescription = "",
            modifier = Modifier
                .drawBehind {
                    rotate(rotateAnimation.value) {
                        drawCircle(
                            Brush.horizontalGradient(
                                colors = listOf(Saffron, Background)
                            ), style = Stroke(8f)
                        )
                    }
                }
                .size(62.dp)
                .padding(8.dp)
                .clip(CircleShape)
        )
    }
}