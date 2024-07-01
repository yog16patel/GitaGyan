package com.yogi.gitagyan.ui.slokadetails

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogi.gitagyan.R
import com.yogi.gitagyan.commonui.GitaCenterAlignedTopAppBar
import com.yogi.gitagyan.commonui.TextComponent
import com.yogi.gitagyan.commonui.TextComponentDevnagri
import com.yogi.gitagyan.models.SlokUi
import com.yogi.gitagyan.ui.appbar.AppbarState
import com.yogi.gitagyan.ui.theme.Dimensions
import com.yogi.gitagyan.ui.theme.LightSaffron
import com.yogi.gitagyan.ui.theme.Saffron
import com.yogi.gitagyan.ui.util.GitaContentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterOverviewPaneContent(
    slokaDetailsPageState: SlokaDetailsPageState,
    onBackPressed: () -> Unit,
    actionButtonClicked: () -> Unit,
    nextClicked: () -> Unit,
    prevClicked: () -> Unit,
    showPrevious : Boolean,
    showNext: Boolean,
    navigateToDetail: (Int, contentType: GitaContentType) -> Unit
) {

    val lazyColumnState = rememberLazyListState()
    if(slokaDetailsPageState.lastSelectedSloka > 1) {
        LaunchedEffect(key1 = slokaDetailsPageState, block = {
            lazyColumnState.animateScrollToItem(slokaDetailsPageState.lastSelectedSloka)
        })
    }
    val slokaList = slokaDetailsPageState.chapterDetailsItems?.slokUiEntityList ?: emptyList()
    val appBarState by remember {
        mutableStateOf(AppbarState())
    }
    appBarState.title = slokaDetailsPageState.chapterDetailsItems?.chapterNumber ?: ""
    var expand by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            GitaCenterAlignedTopAppBar(
                appBarState = appBarState,
                backButtonClicked = {
                    onBackPressed()
                },
                actionButtonClicked = actionButtonClicked)
        }, content = { innerPadding ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(innerPadding)
            ) {

                LazyColumn(
                    state = lazyColumnState,
                    modifier = Modifier.padding(
                        start = Dimensions.gitaPadding,
                        end = Dimensions.gitaPadding,

                        )
                ) {
                    item {
                        TextComponentDevnagri(
                            modifier = Modifier
                                .padding(
                                    vertical = Dimensions.gitaPadding,
                                    horizontal = Dimensions.gitaPadding
                                )
                                .fillMaxWidth(),
                            text = slokaDetailsPageState.chapterDetailsItems?.chapterTitle ?: "",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 25.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        TextComponentDevnagri(
                            modifier = Modifier
                                .padding(start = Dimensions.gitaPadding)
                                .fillMaxWidth()
                                .animateContentSize(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioLowBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                                .clickable() {
                                    expand = !expand
                                },
                            fontSize = 16.sp,
                            text = slokaDetailsPageState.chapterDetailsItems?.description ?: "",
                            lineHeight = 25.sp,
                            maxLines = if (!expand) 3 else Int.MAX_VALUE
                        )
                        TextComponent(
                            text = if (!expand) "Show More ⬇" else "Show Less ⬆",
                            modifier = Modifier
                                .padding(start = Dimensions.gitaPadding)
                                .clickable {
                                    expand = !expand
                                },
                            fontWeight = FontWeight.SemiBold,
                            color = Saffron
                        )
                    }
                    items(slokaList.size) { index ->
                        OverviewSingleItem(slokUi = slokaList[index], index = index) {
                            navigateToDetail(it, GitaContentType.SINGLE_PANE)
                        }
                    }
                    item {
                        NextPrevButton(
                            showPrevious = showPrevious,
                            showNext = showNext,
                            previousClicked = { prevClicked() },
                            nextClicked = { nextClicked() })
                    }
                }

            }
        })
}

@Composable
private fun OverviewSingleItem(
    slokUi: SlokUi,
    index: Int,
    selectedSlokaNumber: (slokIndex: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                onClick = { selectedSlokaNumber(index) },
                indication = rememberRipple(bounded = true)
            )
    ) {

        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = R.drawable.feather_svgrepo_com),
                contentDescription = "Icon",
                tint = Saffron
            )
            TextComponentDevnagri(
                modifier = Modifier.padding(start = Dimensions.gitaPadding),
                text = slokUi.slokaNumber,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(10.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_forward_next),
                contentDescription = "Open",
                tint = Saffron
            )
        }

        TextComponentDevnagri(
            text = slokUi.slokaTranslation,
            fontSize = 16.sp,
            lineHeight = 25.sp
        )
        Spacer(
            modifier = Modifier
                .padding(top = Dimensions.gitaPaddingHalf, bottom = Dimensions.gitaPaddingHalf)
                .fillMaxWidth()
                .height(2.dp)
                .background(color = LightSaffron)
        )
    }

}