package com.yogi.gitagyan.ui.slokadetails

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogi.gitagyan.commonui.TextComponent
import com.yogi.gitagyan.commonui.TextComponentDevnagri
import com.yogi.gitagyan.ui.slokadetails.SlokaDetails.Companion.devnagriFontsize
import com.yogi.gitagyan.ui.slokadetails.SlokaDetails.Companion.lineHeight
import com.yogi.gitagyan.ui.slokadetails.SlokaDetails.Companion.slokTitleFontsize
import com.yogi.gitagyan.ui.theme.Background
import com.yogi.gitagyan.ui.theme.Black
import com.yogi.gitagyan.ui.theme.Dimensions.gitaPadding
import com.yogi.gitagyan.ui.theme.Dimensions.gitaPadding2x
import com.yogi.gitagyan.ui.theme.Saffron
import com.yogi.gitagyan.ui.theme.SlokaBackgroud
import com.yogi.gitagyan.R
import com.yogi.gitagyan.commonui.CircleButton
import com.yogi.gitagyan.commonui.GitaCenterAlignedTopAppBar
import com.yogi.gitagyan.commonui.GitaLinearProgressBar
import com.yogi.gitagyan.commonui.GitaProgressbarState
import com.yogi.gitagyan.ui.appbar.AppbarState
import com.yogi.gitagyan.ui.theme.Dimensions.gitaPadding6x
import com.yogi.gitagyan.ui.util.mapToSlokaIndexToAppSlokaNumber
import com.yogi.gitagyan.ui.util.mapToSlokaNumberWithTheAppSlokaIndex
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlokaDetailsScreen(
    slokaDetailsPageState: SlokaDetailsPageState,
    selectedSlokNumber: Int,
    closeDetailScreen: () -> Unit = {},
    selectedSlokaNumber: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val chapterInfo = slokaDetailsPageState.chapterDetailsItems
    val list = chapterInfo?.slokUiEntityList ?: emptyList()

    val appState by remember {
        mutableStateOf(AppbarState())
    }

    var pagerState by remember(key1 = selectedSlokNumber) {
        mutableStateOf(PagerState(initialPage = selectedSlokNumber))
    }

    LaunchedEffect(key1 = Unit) {
        pagerState.scrollToPage(selectedSlokNumber)
    }

    val listSize = if (list.isNotEmpty()) list.size else 1

    var selectedSlokIndexNumber by remember {
        mutableStateOf(list.mapToSlokaIndexToAppSlokaNumber(selectedSlokNumber))
    }
    var progressbarState by remember {
        mutableStateOf(GitaProgressbarState())
    }
    var currentItem by remember {
        mutableStateOf(slokaDetailsPageState.lastSelectedSloka)
    }
    var showGotoSlokDialog by remember {
        mutableStateOf(false)
    }

    if (showGotoSlokDialog) {
        GotoSlokComposableDialog(
            showDialog = showGotoSlokDialog,
            selectedSloka = selectedSlokIndexNumber,
            slokaNumber = slokaDetailsPageState.totalSlokaList,
            onDismiss = {
                showGotoSlokDialog = false
            },
            onItemSelected = {
                selectedSlokIndexNumber = it
                coroutineScope.launch {
                    pagerState.scrollToPage(list.mapToSlokaNumberWithTheAppSlokaIndex(it))
                }
                showGotoSlokDialog = false
            }
        )
    }

    progressbarState = progressbarState.copy(
        currentItem = currentItem.toFloat(),
        totalItem = listSize.toFloat()
    )

    val title = stringResource(
        id = R.string.chapter_number,
        chapterInfo?.chapterNumber ?: ""
    )
    appState.title = title

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            currentItem = it + 1
        }
    }

    Column {
        GitaCenterAlignedTopAppBar(
            appBarState = appState,
            showActionButton = true,
            backButtonClicked = {
                closeDetailScreen()
            },
            actionButtonClicked = {
                showGotoSlokDialog = true
            })

        GitaLinearProgressBar(
            progressbarState = progressbarState
        )


        HorizontalPager(
            pageCount = list.size,
            state = pagerState
        ) { page ->
            val sloka = list[page]
            SlokaComposable(
                slokaNumber = sloka.slokaNumber,
                currentPage = pagerState.currentPage,
                lastSlokaNumber = list.size,
                sanskritSloka = sloka.slokaSanskrit,
                sloka = sloka.slokaTranslation,
                previousClicked = {
                    coroutineScope.launch {
                        if (pagerState.currentPage > 0) {
                            selectedSlokIndexNumber =
                                list.mapToSlokaIndexToAppSlokaNumber(pagerState.currentPage - 1)
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            selectedSlokaNumber(pagerState.currentPage)
                        }
                    }

                },
                nextClicked = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < list.size - 1) {
                            selectedSlokIndexNumber =
                                list.mapToSlokaIndexToAppSlokaNumber(pagerState.currentPage + 1)
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            selectedSlokaNumber(pagerState.currentPage)
                        }

                    }
                }
            )
        }
    }
}


@Composable
private fun SlokaComposable(
    slokaNumber: String,
    currentPage: Int,
    lastSlokaNumber: Int,
    sanskritSloka: String = "",
    sloka: String,
    previousClicked: () -> Unit,
    nextClicked: () -> Unit,
) {

    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Card(
                colors = CardDefaults.cardColors(
                    contentColor = Black,
                    containerColor = SlokaBackgroud,
                    disabledContainerColor = SlokaBackgroud,
                    disabledContentColor = Black
                ),
                modifier = Modifier.padding(all = gitaPadding2x),
            ) {
                Column(
                    modifier = Modifier
                        .defaultMinSize(300.dp)
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = gitaPadding, end = gitaPadding, top = gitaPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(gitaPadding2x))
                    TextComponent(
                        text = stringResource(id = R.string.slok_number, slokaNumber),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = slokTitleFontsize,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(gitaPadding2x))
                    TextComponentDevnagri(
                        text = sanskritSloka,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = lineHeight,
                        fontSize = devnagriFontsize,
                        textAlign = TextAlign.Center
                    )
                }
            }

            TextComponentDevnagri(
                text = sloka,
                fontSize = devnagriFontsize,
                lineHeight = lineHeight,
                modifier = Modifier.padding(
                    start = gitaPadding2x,
                    end = gitaPadding2x,
                    bottom = gitaPadding6x
                )
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(gitaPadding2x)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentPage > 0) {
                CircleButton(
                    border = Saffron,
                    iconId = painterResource(id = R.drawable.ic_baseline_arrow_back)
                ) {
                    previousClicked()
                }
            } else {
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                )
            }

            if (currentPage < lastSlokaNumber - 1) {
                CircleButton(
                    border = Saffron,
                    iconId = painterResource(id = R.drawable.ic_baseline_arrow_forward)
                ) {
                    nextClicked()
                }
            }
        }
    }
}

class SlokaDetails {
    companion object {
        val lineHeight: TextUnit = 30.sp
        val devnagriFontsize: TextUnit = 18.sp
        val slokTitleFontsize: TextUnit = 22.sp
    }
}