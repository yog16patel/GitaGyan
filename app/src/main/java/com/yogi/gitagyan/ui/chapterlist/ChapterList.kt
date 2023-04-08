package com.yogi.gitagyan.ui.chapterlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogi.gitagyan.commonui.TextComponent
import com.yogi.gitagyan.ui.theme.Background
import com.yogi.gitagyan.ui.theme.Dimensions
import com.yogi.gitagyan.ui.theme.Saffron
import com.yogi.gitagyan.ui.theme.TextWhite
import com.yogi.gitagyan.ui.viewmodels.GitaGyanViewModel
import com.yogi.gitagyan.R
import com.yogi.gitagyan.commonui.ChooseLanguageDialogUi
import com.yogi.gitagyan.commonui.GitaAlertDialogNew
import com.yogi.gitagyan.commonui.ImageBorderAnimation
import com.yogi.domain.entities.PreferredLanguage
import com.yogi.gitagyan.LanguageState
import com.yogi.gitagyan.commonui.GitaTopAppBar
import com.yogi.gitagyan.models.ChapterInfoItemUi
import com.yogi.gitagyan.ui.appbar.AppbarState


@Composable
fun ChapterListScreen(
    viewModel: GitaGyanViewModel,
    navigateToNext: () -> Unit,
    onBackButtonPressed: () -> Unit
) {
    ChapterList(
        viewModel = viewModel,
        navigateToNext = navigateToNext,
        onBackButtonPressed = onBackButtonPressed
    )
}

@Composable
private fun ChapterList(
    viewModel: GitaGyanViewModel,
    navigateToNext: () -> Unit,
    onBackButtonPressed: () -> Unit

) {
    val chaptersState by viewModel.chapterListPageState.collectAsState()
    viewModel.getLanguagePreference()
    if (chaptersState.isLoading) ImageBorderAnimation()
    else {
        val languageState by viewModel.languageState.collectAsState()
        val appbarState by remember {
            mutableStateOf(AppbarState())
        }

        val title =
            if (languageState.preferredLanguage == PreferredLanguage.ENGLISH) stringResource(
                id = R.string.chapters, ""
            )
            else stringResource(id = R.string.chapters_hindi)
        appbarState.title = title

        ChapterList(
            chapterListPageState = chaptersState,
            languageState = languageState,
            appbarState = appbarState,
            onBackButtonPressed = onBackButtonPressed,
            selectedLanguage = {
                viewModel.setLanguagePreferences(it)
            },
            selectedChapter = {
                viewModel.updateSelectedChapter(it)
                navigateToNext()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChapterList(
    chapterListPageState: ChapterListPageState,
    languageState: LanguageState,
    appbarState: AppbarState,
    selectedLanguage: (PreferredLanguage) -> Unit,
    selectedChapter: (Int) -> Unit,
    onBackButtonPressed: () -> Unit
) {

    var showDialog by remember {
        mutableStateOf(false)
    }
    val languageArray: Array<String> = stringArrayResource(id = R.array.language_array)
    val defaultSelectedIem = languageArray[languageState.preferredLanguage.index]

    if (showDialog) {
        GitaAlertDialogNew(dialogOpen = true, content = {
            ChooseLanguageDialogUi(
                items = languageArray,
                defaultSelectedIem = defaultSelectedIem,
                onDismissDialog = {
                    val selection = PreferredLanguage.indexToEnum(it)
                    selectedLanguage(selection)
                    showDialog = false
                }
            )
        })
    }

    Scaffold(topBar = {
        GitaTopAppBar(
            appbarState,
            backButtonClicked = { onBackButtonPressed() },
            actionButtonClicked = {
                showDialog = true
            })
    }) {

        Column(
            modifier = Modifier
                .padding(it)
                .background(Background)
        ) {
            LazyColumn {
                item {

                }
                items(chapterListPageState.chapterInfoItems.size) { i ->
                    val chapter = chapterListPageState.chapterInfoItems[i]

                    ChapterItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val chapterNumber = chapter.chapterNumber.toInt()
                                selectedChapter(chapterNumber)
                            }
                            .padding(
                                vertical = Dimensions.gitaPadding,
                                horizontal = Dimensions.gitaPadding2x
                            ),
                        chapter = chapter
                    )
                }
            }
        }
    }
}
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