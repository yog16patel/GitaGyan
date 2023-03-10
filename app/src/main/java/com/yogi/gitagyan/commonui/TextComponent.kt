package com.yogi.gitagyan.commonui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.yogi.gitagyan.ui.theme.Black
import com.yogi.gitagyan.ui.theme.devnagriFontFamily
import com.yogi.gitagyan.ui.theme.fontFamily

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Black,
    fontWeight : FontWeight = FontWeight.Normal,
    fontSize : TextUnit = TextUnit.Unspecified,
    textAlign : TextAlign = TextAlign.Left
) {
    Text(
        text = text,
        modifier = modifier,
        fontFamily = fontFamily,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}
@Composable
fun TextComponentDevnagri(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Black,
    fontWeight : FontWeight = FontWeight.Normal,
    fontSize : TextUnit = TextUnit.Unspecified,
    textAlign : TextAlign = TextAlign.Left,
    lineHeight : TextUnit = TextUnit.Unspecified
) {
    Text(
        text = text,
        modifier = modifier,
        fontFamily = devnagriFontFamily,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        lineHeight = lineHeight
    )
}
