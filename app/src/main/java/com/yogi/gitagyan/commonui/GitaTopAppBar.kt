package com.yogi.gitagyan.commonui

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yogi.gitagyan.ui.appbar.AppbarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitaTopAppBar(
    appBarState: AppbarState
) {
    TopAppBar(
        title = {
            TextComponent(
                text = appBarState.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .wrapContentSize()
            )
        },
        actions = {
            appBarState.action?.invoke(this)
        }
    )
}