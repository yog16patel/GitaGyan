package com.yogi.gitagyan.ui.userhome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogi.domain.entities.PreferredLanguage
import com.yogi.gitagyan.commonui.TextComponent
import com.yogi.gitagyan.ui.theme.Background
import com.yogi.gitagyan.ui.theme.Dimensions.gitaPadding
import com.yogi.gitagyan.ui.theme.Dimensions.gitaPadding2x
import com.yogi.gitagyan.ui.theme.Saffron
import com.yogi.gitagyan.ui.theme.TextWhite
import com.yogi.gitagyan.R
import com.yogi.gitagyan.commonui.ChooseLanguageDialogUi
import com.yogi.gitagyan.commonui.GitaAlertDialogNew
import com.yogi.gitagyan.commonui.GitaAppBarUserHomeScreen
import com.yogi.gitagyan.ui.theme.Dimensions.gitaPadding3x
import com.yogi.gitagyan.ui.theme.LikedSlokaBackground
import com.yogi.gitagyan.ui.viewmodels.GitaGyanViewModel

@Composable
fun UserHomeScreen(
    viewModel: GitaGyanViewModel,
    navigateToChapterList: () -> Unit,
    continueReading: () -> Unit
) {

    val currentState = viewModel.currentState.collectAsState()
    var showDialog by remember {
        mutableStateOf(false)
    }
    val languageState by viewModel.languageState.collectAsState()
    val qod by viewModel.qodState.collectAsState()
    val languageArray: Array<String> = stringArrayResource(id = R.array.language_array)
    val defaultSelectedIem = languageArray[languageState.preferredLanguage.index]

    if (showDialog) {
        GitaAlertDialogNew(dialogOpen = true) {
            ChooseLanguageDialogUi(
                items = languageArray,
                defaultSelectedIem = defaultSelectedIem,
                onDismissDialog = {
                    val selection = PreferredLanguage.indexToEnum(it)
                    viewModel.setLanguagePreferences(selection)
                    showDialog = false
                }
            )
        }
    }

    Column {
        GitaAppBarUserHomeScreen(
            actionButtonClicked = {
                showDialog = true
            }
        )
        QuoteBox(qod = qod ?: "")
        CurrentProgress(
            currentProgressFloat = currentState.value.currentProgress.toFloat(),
            stringResource(id = R.string.progress_percent, currentState.value.currentProgress),
            currentState.value.selectedChapterIndex.toString(),
            currentState.value.lastSlokString,
            continueReading = {
                currentState.value.run {
                    viewModel.continueReading(
                        chapterNumber = selectedChapterIndex,
                        slokNumber = selectedSlokIndex
                    )
                }
                continueReading()
            }
        )

        ExploreGita(exporeGitaClicked = {
            viewModel.exploreGita()
            navigateToChapterList()
        })
    }

}

@Composable
fun QuoteBox(modifier: Modifier = Modifier, qod: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(gitaPadding2x)
            .clip(RoundedCornerShape(10.dp))
            .background(Saffron.copy(alpha = 0.2F)),
        contentAlignment = Alignment.Center
    ) {

        TextComponent(
            text = qod,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = gitaPadding2x)
        )

    }
}

@Composable
fun CurrentProgress(
    currentProgressFloat: Float,
    currentProgress: String,
    currentChapter: String,
    currentSlok: String,
    continueReading: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(gitaPadding2x)
            .clip(RoundedCornerShape(10.dp))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Saffron),
        ) {

            Row(
                modifier = Modifier
                    .padding(
                        start = gitaPadding2x,
                        end = gitaPadding2x,
                        top = gitaPadding
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextComponent(
                    text = stringResource(id = R.string.current_progress),
                    fontSize = 15.sp,
                    color = TextWhite,
                    fontWeight = FontWeight.SemiBold,
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_book_icon),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(25.dp)
                )

            }
            Row(
                modifier = Modifier
                    .padding(
                        start = gitaPadding2x,
                        end = gitaPadding2x,
                        top = gitaPadding
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextComponent(
                    text = currentProgress,
                    fontSize = 25.sp,
                    color = TextWhite,
                    fontWeight = FontWeight.SemiBold
                )
                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    TextComponent(
                        text = stringResource(id = R.string.chapters, currentChapter),
                        color = TextWhite,
                        fontWeight = FontWeight.SemiBold,
                    )


                    TextComponent(
                        text = stringResource(id = R.string.slok_name, currentSlok),
                        color = TextWhite,
                        fontWeight = FontWeight.SemiBold,
                    )

                }
            }
            LinearProgressIndicator(
                progress = currentProgressFloat / 100,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = gitaPadding2x,
                        end = gitaPadding2x,
                        top = gitaPadding
                    )
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = gitaPadding2x, vertical = gitaPadding3x),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TextWhite),
                onClick = {
                    continueReading()
                },
            ) {
                TextComponent(text = stringResource(id = R.string.continue_reading))
            }

        }
    }
}


@Composable
fun LikedSlokas(
    likedSloka: String
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(start = gitaPadding2x, end = gitaPadding2x)
            .clip(RoundedCornerShape(10.dp))
            .background(LikedSlokaBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(gitaPadding2x)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_heartliked_icon),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopEnd),
                tint = Background
            )

            TextComponent(
                text = likedSloka,
                color = TextWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.align(
                    Alignment.CenterStart
                )
            )
            TextComponent(
                text = stringResource(id = R.string.liked_slokas),
                color = TextWhite,
                fontSize = 15.sp,
                modifier = Modifier.align(
                    Alignment.BottomStart
                )
            )
        }
    }
}

@Composable
fun ExploreGita(
    exporeGitaClicked: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = gitaPadding2x),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Saffron),
        onClick = {
            exporeGitaClicked()
        }
    ) {
        TextComponent(text = stringResource(id = R.string.explore_gita), color = Saffron)
    }

}







