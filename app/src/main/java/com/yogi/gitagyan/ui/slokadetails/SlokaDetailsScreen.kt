package com.yogi.gitagyan.ui.slokadetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.yogi.gitagyan.ui.theme.SlokaBackgroud
import com.yogi.gitagyan.R
import com.yogi.gitagyan.commonui.GitaCenterAlignedTopAppBar
import com.yogi.gitagyan.commonui.GitaLinearProgressBar
import com.yogi.gitagyan.commonui.GitaProgressbarState
import com.yogi.gitagyan.models.SlokUi
import com.yogi.gitagyan.ui.appbar.AppbarState
import com.yogi.gitagyan.ui.theme.Dimensions.gitaPadding6x
import com.yogi.gitagyan.ui.util.GitaContentType
import com.yogi.gitagyan.ui.util.mapToSlokaIndexToAppSlokaNumber

@Composable
fun SlokaDetailsScreen(
    modifier: Modifier = Modifier,
    appBarState: AppbarState,
    slokaDetailsPageState: SlokaDetailsPageState,
    closeDetailScreen: () -> Unit = {},
    viewModel: GitaGyanViewModel
) {
    var progressbarState by remember {
        mutableStateOf(GitaProgressbarState())
    }
    val listSize by remember {
        mutableStateOf((slokaDetailsPageState.chapterDetailsItems?.slokUiEntityList?.size ?: 0) - 1)
    }
    var currentItem by remember(slokaDetailsPageState) {
        mutableStateOf(slokaDetailsPageState.lastSelectedSloka)
    }

    var showGotoSlokDialog by remember {
        mutableStateOf(false)
    }

    if (showGotoSlokDialog) {
        GotoItemComposableDialog(
            title = stringResource(id = R.string.select_slok),
            showDialog = showGotoSlokDialog,
            selectedNumber = viewModel.getLastSelectedSloka(currentItem),
            listNumbers = slokaDetailsPageState.totalSlokaList,
            onDismiss = {
                showGotoSlokDialog = false
            },
            onItemSelected = {
                viewModel.goToSelectedSloka(it)
                showGotoSlokDialog = false
            }
        )
    }

    progressbarState = progressbarState.copy(
        currentItem = currentItem.toFloat(),
        totalItem = listSize.toFloat()
    )

    Column(
        modifier = modifier
    ) {
        GitaCenterAlignedTopAppBar(
            appBarState = appBarState,
            backButtonClicked = {
                closeDetailScreen()
            },
            actionButtonClicked = {
                showGotoSlokDialog = true
            },
        )
        GitaLinearProgressBar(
            progressbarState = progressbarState
        )
        SlokaDetailWithNavigationInBox(
            modifier = Modifier,
            listSize = listSize,
            slokaDetailsPageState = slokaDetailsPageState,
            closeDetailScreen = closeDetailScreen,
            indexChanged = { index -> currentItem = index },
            viewModel = viewModel
        )
    }
}

@Composable
private fun SlokaDetailWithNavigationInBox(
    modifier: Modifier = Modifier,
    listSize: Int,
    slokaDetailsPageState: SlokaDetailsPageState,
    closeDetailScreen: () -> Unit = {},
    indexChanged: (index: Int) -> Unit = {},
    viewModel: GitaGyanViewModel
) {
    var currentIndex by remember {
        mutableStateOf(slokaDetailsPageState.lastSelectedSloka)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        SlokaDetailsScreen(
            slokaDetailsPageState = slokaDetailsPageState,
            currentIndex = currentIndex,
            closeDetailScreen = { closeDetailScreen() },
        )
        HandleNextPrevNavigation(
            showPrevious = currentIndex > 0,
            showNext = currentIndex < listSize,
            previousClicked = {
                val index = slokaDetailsPageState.lastSelectedSloka - 1
                indexChanged(index)
                currentIndex = index
                viewModel.setLastSelectedSloka(index)

            },
            nextClicked = {
                val index = slokaDetailsPageState.lastSelectedSloka + 1
                indexChanged(index)
                currentIndex = index
                viewModel.setLastSelectedSloka(index)

            },
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlokaDetailsScreen(
    slokaDetailsPageState: SlokaDetailsPageState,
    currentIndex: Int,
    closeDetailScreen: () -> Unit = {},
) {
    val chapterInfo = slokaDetailsPageState.chapterDetailsItems
    val list = chapterInfo?.slokUiEntityList ?: emptyList()
    Column {
        SlokaDetailLayout(
            modifier = Modifier,
            pageCount = list.size,
            currentIndex = currentIndex,
            sloka = list[slokaDetailsPageState.lastSelectedSloka]
        )
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SlokaDetailLayout(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentIndex: Int,
    sloka: SlokUi
) {
    val pagerState = rememberPagerState()
    LaunchedEffect(key1 = currentIndex) {
        pagerState.animateScrollToPage(currentIndex)

    }

    HorizontalPager(
        pageCount = pageCount,
        state = pagerState
    ) { page ->
        SlokaComposable(
            slokaNumber = sloka.slokaNumber,
            sanskritSloka = sloka.slokaSanskrit,
            sloka = sloka.slokaTranslation
        )
    }
}

@Composable
private fun SlokaComposable(
    slokaNumber: String,
    sanskritSloka: String = "",
    sloka: String,
    scrollState: ScrollState = rememberScrollState()
) {

    Column(
        modifier = Modifier
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

}

class SlokaDetails {
    companion object {
        val lineHeight: TextUnit = 30.sp
        val devnagriFontsize: TextUnit = 18.sp
        val slokTitleFontsize: TextUnit = 22.sp
    }
}