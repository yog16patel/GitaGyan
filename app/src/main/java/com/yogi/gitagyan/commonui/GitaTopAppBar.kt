package com.yogi.gitagyan.commonui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogi.gitagyan.ui.appbar.AppbarState
import com.yogi.gitagyan.ui.theme.Black
import com.yogi.gitagyan.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitaTopAppBar(
    appBarState: AppbarState,
    actionButtonClicked: () -> Unit,
    backButtonClicked: () -> Unit
) {
    TopAppBar(

        navigationIcon = {
            if (appBarState.showBackButton) {
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
            if(appBarState.showActionButton) {
                IconButton(
                    modifier = Modifier
                        .padding(end = Dimensions.gitaPadding2x)
                        .size(24.dp),
                    onClick = {
                        actionButtonClicked()
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings",
                        tint = Black

                    )
                }
            }
        }
    )
}