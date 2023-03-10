package com.yogi.gitagyan.commonui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.yogi.gitagyan.ui.theme.Background
import com.yogi.gitagyan.ui.theme.Dimensions.gitaPaddingHalf

@Composable
fun OutLinedButton(
    modifier: Modifier = Modifier,
    text: String,
    border: Color,
    noIcon: Boolean = false,
    iconAtStart: Boolean = true,
    roundedPercent: Int = 15,
    textColor: Color,
    iconId: Painter?=null,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(1.dp, border),
        shape = RoundedCornerShape(roundedPercent), // = 50% percent
        // or shape = CircleShape
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Background,
            contentColor = textColor
        )
    ) {
        if (iconAtStart && !noIcon) iconId?.let {
            Icon(
                painter = it,
                contentDescription = "",
                modifier = Modifier.padding(end = gitaPaddingHalf)
            )
        }
        Text(text = text)

        if (!iconAtStart && !noIcon) iconId?.let {
            Icon(
                painter = it,
                contentDescription = "",
                modifier = Modifier.padding(start = gitaPaddingHalf)
            )
        }

    }

}