package com.yogi.gitagyan.ui.slokadetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yogi.gitagyan.R
import com.yogi.gitagyan.commonui.CircleButton
import com.yogi.gitagyan.ui.theme.Dimensions
import com.yogi.gitagyan.ui.theme.Saffron

@Composable
fun NextPrevButton(
    showPrevious: Boolean,
    showNext: Boolean,
    previousClicked: () -> Unit,
    nextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .padding(Dimensions.gitaPadding2x)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (showPrevious) {
            CircleButton(
                border = Saffron,
                iconId = painterResource(id = R.drawable.ic_baseline_arrow_back)
            ) {
                previousClicked()
            }
        } else {
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (showNext) {
            CircleButton(
                border = Saffron,
                iconId = painterResource(id = R.drawable.ic_baseline_arrow_forward)
            ) {
                nextClicked()
            }
        }
    }
}
@Composable
fun HandleNextPrevNavigation(
    showPrevious: Boolean,
    showNext: Boolean,
    previousClicked: () -> Unit,
    nextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    NextPrevButton(
        showPrevious = showPrevious,
        showNext = showNext,
        previousClicked = {
            previousClicked()
        },
        nextClicked = {
            nextClicked()
        },
        modifier = modifier
    )
}