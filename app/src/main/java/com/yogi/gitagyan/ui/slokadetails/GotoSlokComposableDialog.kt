package com.yogi.gitagyan.ui.slokadetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yogi.gitagyan.R
import com.yogi.gitagyan.commonui.GitaAlertDialogNew
import com.yogi.gitagyan.commonui.TextComponent
import com.yogi.gitagyan.ui.theme.Dimensions
import com.yogi.gitagyan.ui.theme.Saffron


@Composable
fun GotoSlokComposableDialog(
    modifier: Modifier = Modifier,
    slokaNumber: List<Int>,
    selectedSloka : Int,
    showDialog: Boolean,
    onDismiss : () -> Unit,
    onItemSelected: (Int) -> Unit
) {


    var selectedSlokRem by remember {
        mutableStateOf(selectedSloka)
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
                    text = stringResource(id = R.string.select_slok),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )

                LazyVerticalGrid(columns = GridCells.Fixed(COLUMN_SIZE)){
                    items(slokaNumber.size) {
                        Item(
                            slokaNumber[it],
                            modifier = Modifier.weight(1f),
                            slokaNumber[it] == selectedSlokRem
                        ) {
                            selectedSlokRem = it
                            onItemSelected(selectedSlokRem)
                        }
                    }
                }

        }

    })
}
@Composable
fun Item(number: Int,modifier: Modifier,showSelected : Boolean, selectedNumber: (Int)->Unit) {

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