package com.yogi.gitagyan.ui.slokadetails

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogi.gitagyan.commonui.OutLinedButton
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
import com.yogi.gitagyan.ui.viewmodels.GitaGyanViewModel
import com.yogi.gitagyan.R
import com.yogi.gitagyan.ui.appbar.AppbarState
import com.yogi.gitagyan.ui.model.PreferredLanguage
import com.yogi.gitagyan.ui.theme.Dimensions.gitaPadding6x
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlokaDetails(
    viewModel: GitaGyanViewModel,
    onComposing: (AppbarState) -> Unit
) {
    val pagestate = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val slokaDetailsPageState = viewModel.slokaDetailsPageState.collectAsState()
    val appState = viewModel.languageState.collectAsState()
    val chapterInfo = slokaDetailsPageState.value.chapterInfoItems
    val list = chapterInfo?.slokas ?: emptyList()

    val title = stringResource(
        id = R.string.chapter_number,
        chapterInfo?.chapter_number ?: ""
    )

    LaunchedEffect(key1 = true) {
        mutableStateOf(
            onComposing(
                AppbarState(
                    title = title,
                    action = null
                )
            )
        )
    }


    HorizontalPager(
        pageCount = list.size,
        state = pagestate
    ) {
        val sloka = list[it]
        SlokaComposable(
            slokaNumber = sloka.number,
            currentPage = pagestate.currentPage,
            lastSlokaNumber = list.size,
            sanskritSloka = sloka.sanskrit,
            sloka = if (appState.value.preferredLanguage == PreferredLanguage.ENGLISH) sloka.translations.EN
            else sloka.translations.HN,
            previousClicked = {
                coroutineScope.launch {
                    if (pagestate.currentPage > 0)
                        pagestate.animateScrollToPage(pagestate.currentPage - 1)
                }
            },
            nextClicked = {
                coroutineScope.launch {
                    if (pagestate.currentPage < list.size - 1)
                        pagestate.animateScrollToPage(pagestate.currentPage + 1)
                }
            }
        )
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
                OutLinedButton(
                    text = "Previous",
                    border = Saffron,
                    textColor = Saffron,
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
                OutLinedButton(
                    text = "Next",
                    iconAtStart = false,
                    border = Saffron,
                    textColor = Saffron,
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