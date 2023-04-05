package com.yogi.gitagyan.commonui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yogi.gitagyan.R
import com.yogi.gitagyan.ui.theme.Black
import com.yogi.gitagyan.ui.theme.Dimensions
import com.yogi.gitagyan.ui.theme.Saffron

@Composable
fun GitaAppBarUserHomeScreen(
    actionButtonClicked : () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Divider(
            color = Saffron,
            modifier = Modifier
                .height(50.dp)
                .width(4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimensions.gitaPadding2x
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {

                TextComponent(
                    text = stringResource(id = R.string.namaste),
                    fontWeight = FontWeight.SemiBold
                )

                TextComponent(
                    text = stringResource(id = R.string.bhakt),
                    color = Saffron
                )
            }
            IconButton(
                modifier = Modifier
                    .padding(end = Dimensions.gitaPadding2x)
                    .size(24.dp),
                onClick = {
                    actionButtonClicked()
                }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Back",
                    tint = Black

                )
            }
        }
    }
}