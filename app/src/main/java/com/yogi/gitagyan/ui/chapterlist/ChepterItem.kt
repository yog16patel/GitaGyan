package com.yogi.gitagyan.ui.chapterlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogi.gitagyan.R
import com.yogi.gitagyan.commonui.TextComponent
import com.yogi.gitagyan.models.ChapterInfoItemUi
import com.yogi.gitagyan.ui.theme.Dimensions
import com.yogi.gitagyan.ui.theme.Saffron
import com.yogi.gitagyan.ui.theme.TextWhite

@Composable
fun ChapterItem(
    modifier: Modifier,
    chapter: ChapterInfoItemUi
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(Saffron)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimensions.gitaPadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            TextComponent(
                text = stringResource(
                    id = R.string.chapter_number_with_sanksrit,
                    chapter.chapterNumber,
                    chapter.name
                ),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = TextWhite,
                modifier = Modifier
                    .padding(
                        horizontal = Dimensions.gitaPadding2x
                    ),
            )
            TextComponent(
                text = chapter.translation,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = TextWhite,
                modifier = Modifier
                    .padding(
                        bottom = Dimensions.gitaPaddingQuarter,
                        start = Dimensions.gitaPadding2x,
                        end = Dimensions.gitaPadding
                    )

            )
        }
    }
}