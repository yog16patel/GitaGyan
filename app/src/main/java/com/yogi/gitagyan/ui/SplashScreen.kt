package com.yogi.gitagyan.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.yogi.gitagyan.R
import com.yogi.gitagyan.commonui.TextComponent
import com.yogi.gitagyan.ui.theme.Background
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    finished: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(key1 = true){
            delay(1000)
            finished()
        }
        Image(painter = painterResource(id = R.drawable.gita_gayan_launcher_icon), contentDescription = "")
        TextComponent(
            text = stringResource(id = R.string.splash_title_name).uppercase(),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        TextComponent(text = stringResource(id = R.string.splash_slogan),  fontSize = 15.sp)

    }

}