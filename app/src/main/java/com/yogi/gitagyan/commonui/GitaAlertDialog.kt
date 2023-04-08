package com.yogi.gitagyan.commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.yogi.gitagyan.ui.theme.Background
import com.yogi.gitagyan.ui.theme.Dimensions

@Composable
fun GitaAlertDialogNew (
    dialogOpen: Boolean,
    content: @Composable ()->Unit,
    onDismiss : () -> Unit = {}
) {

    if(dialogOpen) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .background(Background)
                    .padding(Dimensions.gitaPadding2x),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}