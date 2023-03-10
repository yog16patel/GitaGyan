package com.yogi.gitagyan.commonui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import com.yogi.gitagyan.ui.theme.Black
import com.yogi.gitagyan.ui.theme.Dimensions
import com.yogi.gitagyan.ui.theme.Saffron

@Composable
fun GitaRadioGroup(
    modifier: Modifier = Modifier,
    defaultSelectedIem: String,
    items: List<String>,
    selectedItemIndex: (Int) -> Unit
) {
    val selectedValue = remember {
        mutableStateOf(defaultSelectedIem)
    }

    val isSelected: (String) -> Boolean = { selectedValue.value == it }
    val onStateChanged: (String) -> Unit = { selectedValue.value = it }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items.forEachIndexed { index, item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = isSelected(item),
                        onClick = {
                            onStateChanged(item)
                            selectedItemIndex(index)
                        },
                        role = Role.RadioButton
                    )
                    .padding(start = Dimensions.gitaPadding)
            ) {
                RadioButton(
                    selected = isSelected(item),
                    onClick = {
                        selectedValue.value = item

                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Saffron,
                        unselectedColor = Black
                    )
                )
                TextComponentDevnagri(text = item, modifier = Modifier.fillMaxWidth())
            }
        }

    }

}