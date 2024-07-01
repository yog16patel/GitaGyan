package com.yogi.gitagyan.ui.slokadetails

import androidx.compose.runtime.Composable
import androidx.window.layout.DisplayFeature
import com.yogi.gitagyan.ui.util.GitaContentType

@Composable
fun ManagePanes(
    viewModel: GitaGyanViewModel,
    displayFeatures: List<DisplayFeature>,
    contentType: GitaContentType,
    slokaDetailsPageState: SlokaDetailsPageState,
    goBack: () -> Unit,
    actionButtonClicked: () -> Unit,
    closeDetailScreen: () -> Unit,
    showPrevious : Boolean,
    showNext: Boolean,
    nextClicked: () -> Unit,
    prevClicked: () -> Unit,
    navigateToDetail: (Int, contentType: GitaContentType) -> Unit

) {
    if (contentType == GitaContentType.DUAL_PANE) {
        DualPaneContent(
            viewModel = viewModel,
            displayFeatures = displayFeatures,
            slokaDetailsPageState = slokaDetailsPageState,
            closeDetailScreen = closeDetailScreen,
            goBack = goBack,
            actionButtonClicked = actionButtonClicked,
            showPrevious = showPrevious,
            showNext = showNext,
            nextClicked = nextClicked,
            prevClicked = prevClicked
        )
    } else {
        SinglePaneContent(
            viewModel = viewModel,
            slokaDetailsPageState = slokaDetailsPageState,
            closeDetailScreen = closeDetailScreen,
            goBack = goBack,
            actionButtonClicked = actionButtonClicked,
            navigateToDetail = navigateToDetail,
            showPrevious = showPrevious,
            showNext = showNext,
            nextClicked = nextClicked,
            prevClicked = prevClicked,
        )

    }
}