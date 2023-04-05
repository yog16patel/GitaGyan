package com.yogi.gitagyan.commonui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.yogi.gitagyan.ui.theme.Dimensions

@Composable
fun GitaFloatingActionButton(
    modifier: Modifier = Modifier,
    iconId : Painter,
    borderColor: Color,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = {onClick()},
        contentColor = borderColor,
        shape = CircleShape,
    ) {
        OutlinedButton(onClick = { onClick() },
            modifier= modifier.size(Dimensions.gitaPadding7x),  //avoid the oval shape
            shape = CircleShape,
            border= BorderStroke(2.dp, borderColor),
            contentPadding = PaddingValues(0.dp),  //avoid the little icon
            colors = ButtonDefaults.outlinedButtonColors(contentColor =  borderColor)
        ) {
            Icon(painter = iconId, contentDescription = "content description")
        }
    }

}