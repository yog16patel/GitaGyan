package com.yogi.gitagyan.ui.slokadetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.window.layout.DisplayFeature
import com.yogi.gitagyan.R
import com.yogi.gitagyan.commonui.ImageBorderAnimation
import com.yogi.gitagyan.ui.util.GitaContentType

@Composable
fun ChapterOverviewScreen(
    viewModel: GitaGyanViewModel,
    contentType: GitaContentType,
    displayFeatures: List<DisplayFeature>,
    closeDetailScreen: () -> Unit,
    goBack: () -> Unit,
    navigateToDetail: (Int, contentType: GitaContentType) -> Unit
) {

    val slokaDetailsPageState by viewModel.slokaDetailsPageState.collectAsState()
    val chapterListPageState by viewModel.chapterListPageState.collectAsState()
    /**
     * When moving from LIST_AND_DETAIL page to LIST page clear the selection and user should see LIST screen.
     */
    LaunchedEffect(key1 = contentType) {
        if (contentType == GitaContentType.SINGLE_PANE && !slokaDetailsPageState.isDetailOpen) {
            closeDetailScreen()
        }
    }

    var showSelectChapterDialog by remember {
        mutableStateOf(false)
    }

    if(showSelectChapterDialog){
        GotoItemComposableDialog(
            title = stringResource(id = R.string.select_chapter),
            listNumbers = (1..18).toList(),
            selectedNumber = chapterListPageState.selectedChapter,
            showDialog = showSelectChapterDialog,
            onDismiss = { showSelectChapterDialog = false },
            onItemSelected = {  chNumber->
                viewModel.updateSelectedChapter(chNumber)
                viewModel.setLastSelectedSloka(1, contentType = GitaContentType.NONE)
                showSelectChapterDialog = false
            })
    }
    when (slokaDetailsPageState.isLoading) {
        true -> ImageBorderAnimation()
        else -> {
            ManagePanes(
                viewModel = viewModel,
                displayFeatures = displayFeatures,
                contentType = contentType,
                slokaDetailsPageState = slokaDetailsPageState,
                goBack = goBack,
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail,
                actionButtonClicked = {
                    showSelectChapterDialog = true
                },
                showPrevious = chapterListPageState.selectedChapter > 1,
                showNext = chapterListPageState.selectedChapter < 18,
                nextClicked = {
                    val chNumber = chapterListPageState.selectedChapter + 1
                    viewModel.updateSelectedChapter(chNumber)
                    viewModel.setLastSelectedSloka(1, contentType = GitaContentType.NONE)
                },
                prevClicked =  {
                    val chNumber = chapterListPageState.selectedChapter - 1
                    viewModel.updateSelectedChapter(chNumber)
                    viewModel.setLastSelectedSloka(1, contentType = GitaContentType.NONE)

                }
            )
        }
    }
}



