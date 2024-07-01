package com.yogi.gitagyan.ui.slokadetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yogi.gitagyan.commonui.GitaAlertDialogNew
import com.yogi.gitagyan.commonui.TextComponent
import com.yogi.gitagyan.ui.theme.Dimensions
import com.yogi.gitagyan.ui.theme.Saffron


@Composable
fun <T>GotoItemComposableDialog(
    modifier: Modifier = Modifier,
    title: String,
    listNumbers: List<T>,
    selectedNumber : T,
    showDialog: Boolean,
    onDismiss : () -> Unit,
    onItemSelected: (T) -> Unit
) {
    var selectedSlokRem by remember {
        mutableStateOf(selectedNumber)
    }

    GitaAlertDialogNew(
        dialogOpen = showDialog,
        onDismiss = onDismiss,
        content = {

            Column(
                modifier = Modifier
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextComponent(
                    text = title,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )

                LazyVerticalGrid(columns = GridCells.Fixed(COLUMN_SIZE)){
                    items(listNumbers) {
                        Item(
                            it,
                            modifier = Modifier.weight(1f),
                            it == selectedSlokRem
                        ) { selected ->
                            selectedSlokRem = selected
                            onItemSelected(selectedSlokRem)
                        }
                    }
                }

        }

    })
}
@Composable
fun<T> Item(number: T,modifier: Modifier,showSelected : Boolean, selectedNumber: (T)->Unit) {

    Text(
        modifier = modifier
            .padding(Dimensions.gitaPadding)
            .clickable {
                selectedNumber(number)
            }
            .drawBehind {
                if (showSelected) {
                    drawCircle(
                        color = Saffron,
                        radius = 16.dp.toPx()
                    )
                }
            },
        textAlign = TextAlign.Center,
        text = number.toString(),
        color = Color.Black
    )
}

const val COLUMN_SIZE = 8