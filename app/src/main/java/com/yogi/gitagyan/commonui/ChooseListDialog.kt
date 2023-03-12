package com.yogi.gitagyan.commonui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogi.gitagyan.R
import com.yogi.gitagyan.ui.theme.Dimensions
import com.yogi.gitagyan.ui.theme.Saffron

@Composable
fun ChooseLanguageDialogUi(
    items: Array<String>,
    defaultSelectedIem: String,
    onDismissDialog: (Int) -> Unit
) {

    var selectedOptionIndex by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(56.dp)
                .padding(bottom = Dimensions.gitaPadding),
            painter = painterResource(id = R.drawable.choose_language_icon),
            contentDescription = "choose language",
            tint = Saffron
        )

        TextComponentDevnagri(
            text = stringResource(id = R.string.choose_your_preferred_language),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(bottom = Dimensions.gitaPadding)
        )
        GitaRadioGroup(
            items = items.toList(),
            defaultSelectedIem = defaultSelectedIem
        ) { index ->
            selectedOptionIndex = index
        }

        OutLinedButton(
            modifier = Modifier.padding(vertical = Dimensions.gitaPadding),
            text = stringResource(
                id = R.string.okay_lets_go
            ),
            border = Saffron,
            textColor = Saffron,
            iconId = null,
            noIcon = true
        ) {
            onDismissDialog(selectedOptionIndex)
        }
    }

}
