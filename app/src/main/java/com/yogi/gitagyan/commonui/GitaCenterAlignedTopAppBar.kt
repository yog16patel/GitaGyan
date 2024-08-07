package com.yogi.gitagyan.commonui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.yogi.gitagyan.ui.appbar.AppbarState
import com.yogi.gitagyan.ui.theme.Background
import com.yogi.gitagyan.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitaCenterAlignedTopAppBar(
    appBarState: AppbarState = AppbarState(),
    backButtonClicked : ()->Unit,
    actionButtonClicked: () -> Unit = {}
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
            if(appBarState.showBackButton) {
                IconButton(onClick = {
                    backButtonClicked()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Black
                    )
                }
            }
        },
        actions = {
            if(appBarState.showBackButton) {
                IconButton(onClick = {
                    actionButtonClicked()
                }) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = "Back",
                        tint = Black
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Background)
    )
}