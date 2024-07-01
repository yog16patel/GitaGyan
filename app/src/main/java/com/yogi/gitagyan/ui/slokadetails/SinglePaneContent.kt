package com.yogi.gitagyan.ui.slokadetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yogi.gitagyan.ui.appbar.AppbarState
import com.yogi.gitagyan.ui.util.GitaContentType

@Composable
fun SinglePaneContent(
    viewModel: GitaGyanViewModel,
    slokaDetailsPageState: SlokaDetailsPageState,
    closeDetailScreen: () -> Unit,
    goBack: () -> Unit,
    actionButtonClicked: () -> Unit,
    showPrevious : Boolean,
    showNext: Boolean,
    nextClicked: () -> Unit,
    prevClicked: () -> Unit,
    navigateToDetail: (Int, GitaContentType) -> Unit
) {
    if (slokaDetailsPageState.isDetailOpen) {
        SlokaDetailsScreen(
            modifier = Modifier,
            appBarState = AppbarState(slokaDetailsPageState.chapterDetailsItems?.chapterNumber?:""),
            slokaDetailsPageState = slokaDetailsPageState,
            closeDetailScreen = closeDetailScreen,
            viewModel = viewModel
        )
    } else {
        ChapterOverviewPaneContent(
            slokaDetailsPageState = slokaDetailsPageState,
            onBackPressed = goBack,
            actionButtonClicked = actionButtonClicked,
            showPrevious = showPrevious,
            showNext = showNext,
            nextClicked = nextClicked,
            prevClicked = prevClicked,
            navigateToDetail = navigateToDetail
        )
    }
}
