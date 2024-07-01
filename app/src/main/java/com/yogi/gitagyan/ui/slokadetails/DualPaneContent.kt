package com.yogi.gitagyan.ui.slokadetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.yogi.gitagyan.ui.appbar.AppbarState

@Composable
fun DualPaneContent(
    viewModel: GitaGyanViewModel,
    displayFeatures: List<DisplayFeature>,
    slokaDetailsPageState: SlokaDetailsPageState,
    goBack: () -> Unit,
    actionButtonClicked : () -> Unit,
    closeDetailScreen: () -> Unit,
    showPrevious : Boolean,
    showNext: Boolean,
    nextClicked: () -> Unit,
    prevClicked: () -> Unit,
) {
    TwoPane(
        first = {
            ChapterOverviewPaneContent(
                slokaDetailsPageState = slokaDetailsPageState,
                onBackPressed = goBack,
                nextClicked = nextClicked,
                prevClicked = prevClicked,
                actionButtonClicked = actionButtonClicked,
                showPrevious = showPrevious,
                showNext = showNext,
                navigateToDetail = { number, type ->
                    viewModel.setLastSelectedSloka(number, type)
                }
            )
        },

        second = {
            SlokaDetailsScreen(
                modifier = Modifier,
                slokaDetailsPageState = slokaDetailsPageState,
                appBarState = AppbarState(
                    slokaDetailsPageState.chapterDetailsItems?.chapterNumber ?: "",
                    showBackButton = false,
                    showActionButton = true
                ),
                closeDetailScreen = closeDetailScreen,
                viewModel = viewModel
            )
        },
        strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
        displayFeatures = displayFeatures
    )
}