package com.yogi.gitagyan.ui.chapterlist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.yogi.gitagyan.commonui.ChooseLanguageDialog
import com.yogi.gitagyan.commonui.GitaAlertDialogNew
import com.yogi.gitagyan.commonui.ImageBorderAnimation
import com.yogi.gitagyan.ui.appbar.AppbarState
import com.yogi.domain.entities.PreferredLanguage
import com.yogi.gitagyan.models.ChapterInfoItemUi
import com.yogi.gitagyan.ui.theme.Black

@Composable
fun ChapterList(
    viewModel: GitaGyanViewModel,
    onComposing: (AppbarState) -> Unit,
    navigateToSlokaComposable: (Int) -> Unit
) {
    val chaptersState by viewModel.chapterListPageState.collectAsState()

    if (chaptersState.isLoading) ImageBorderAnimation()
    else {

        val chapterListPageState = chaptersState
        var showDialog by remember {
            mutableStateOf(false)
        }
        val languageArray: Array<String> = stringArrayResource(id = R.array.language_array)

        val appState = viewModel.languageState.collectAsState()
        val defaultSelectedIem = languageArray[appState.value.preferredLanguage.index]
        val languageState = viewModel.languageState.collectAsState()
        if (showDialog) {
            GitaAlertDialogNew(dialogOpen = true) {
                ChooseLanguageDialog(
                    items = languageArray,
                    defaultSelectedIem = defaultSelectedIem,
                    onDismissDialog = {
                        val selection = PreferredLanguage.indexToEnum(it)
                        Log.e(
                            "Yogesh",
                            "ChapterDetails.kt Selected Language : $selection and index : $it"
                        )
                        viewModel.setLanguagePreferences(preferredLanguage = selection)
                        showDialog = false
                    }
                )
            }
        }
        val title =
            if (languageState.value.preferredLanguage == PreferredLanguage.ENGLISH) stringResource(
                id = R.string.chapters
            )
            else stringResource(id = R.string.chapters_hindi)

        LaunchedEffect(key1 = title) {
            onComposing(
                AppbarState(
                    title = title,
                    action = {
                        IconButton(
                            modifier = Modifier
                                .padding(end = Dimensions.gitaPadding2x)
                                .size(24.dp),
                            onClick = {
                                showDialog = true
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Back",
                                tint = Black

                            )
                        }
                    }
                )
            )
        }

        Column(
            modifier = Modifier.background(Background)
        ) {
            LazyColumn {
                items(chapterListPageState.chapterInfoItems.size) { i ->
                    val chapter = chapterListPageState.chapterInfoItems[i]

                    ChapterItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val chapterNumber = chapter.chapterNumber.toInt()
                                viewModel.getSlokaDetails(chapterNumber = chapterNumber)
                                navigateToSlokaComposable(chapterNumber)
                            }
                            .padding(
                                vertical = Dimensions.gitaPadding,
                                horizontal = Dimensions.gitaPadding2x
                            ),
                        chapter = chapter,
                        preferredLanguage = appState.value.preferredLanguage
                    )
                }
            }
        }
    }
}


@Composable
fun ChapterItem(
    modifier: Modifier,
    chapter: ChapterInfoItemUi,
    preferredLanguage: PreferredLanguage
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