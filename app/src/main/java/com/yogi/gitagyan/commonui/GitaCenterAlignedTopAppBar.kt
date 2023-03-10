package com.yogi.gitagyan.commonui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.yogi.gitagyan.GitaGyanAppState
import com.yogi.gitagyan.ui.appbar.AppbarState
import com.yogi.gitagyan.ui.theme.Background
import com.yogi.gitagyan.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitaCenterAlignedTopAppBar(
    appBarState: AppbarState,
    appState: GitaGyanAppState
) {
    CenterAlignedTopAppBar(
        title = {
            TextComponent(
                text = appBarState.title,
                color = Black,
                fontSize = 18.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                appState.navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Black
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Background)
    )
}