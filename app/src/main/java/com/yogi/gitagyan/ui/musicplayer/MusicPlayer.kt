package com.yogi.gitagyan.ui.musicplayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward30
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yogi.gitagyan.R
import com.yogi.gitagyan.commonui.TextComponent
import com.yogi.gitagyan.ui.theme.DarkSaffron
import com.yogi.gitagyan.ui.theme.TextWhite
import com.yogi.gitagyan.ui.util.verticalGradientScrim

@Composable
fun PlayerContentRegular(
    modifier: Modifier = Modifier,
    musicPlayerService: MediaPlayerService?
) {

    val path = "https://firebasestorage.googleapis.com/v0/b/geetagyan-4f5fe.appspot.com/o/Gita%2FSanskrit%2FChapter%2001.mp3?alt=media&token=e4577766-3df0-4d14-8ef0-90aaf4ea9780"

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalGradientScrim(
                color = DarkSaffron.copy(),
                startYPercentage = 0f,
                endYPercentage = 1f
            )
            .systemBarsPadding()
            .padding(horizontal = 8.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            PlayerImage(
                modifier = Modifier.weight(10f)
            )
            Spacer(modifier = Modifier.height(32.dp))
            PodcastDescription("Simple Title", "Podcast Name")
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(10f)
            ) {
                PlayerSlider("")
                PlayerButtons(
                    Modifier.padding(vertical = 8.dp),
                    play = {
                        musicPlayerService?.playMedia(path)
                    },
                    forward = {
                        musicPlayerService?.forward()
                    },
                    rewind = {
                        musicPlayerService?.rewind()
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun PlayerImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.gita_gayan_launcher_icon),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .sizeIn(maxWidth = 500.dp, maxHeight = 500.dp)
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.medium)
    )
}

@Composable
private fun PodcastDescription(
    title: String,
    podcastName: String
) {
    TextComponent(
        text = title,
        fontWeight = FontWeight.SemiBold,
        color = TextWhite
    )

    TextComponent(
        text = podcastName,
        color = TextWhite
    )

}

@Composable
private fun PlayerSlider(episodeDuration: String?) {
    if (episodeDuration != null) {
        Column(Modifier.fillMaxWidth()) {
            Slider(value = 0f, onValueChange = { })
            Row(Modifier.fillMaxWidth()) {
                Text(text = "0s")
                Spacer(modifier = Modifier.weight(1f))
                Text("10s")
            }
        }
    }
}

@Composable
private fun PlayerButtons(
    modifier: Modifier = Modifier,
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 48.dp,
    play : () -> Unit,
    forward: () ->Unit,
    rewind: () -> Unit

) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val buttonsModifier = Modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }

        Image(
            imageVector = Icons.Filled.SkipPrevious,
            contentDescription = stringResource(R.string.cd_skip_previous),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(TextWhite),
            modifier = buttonsModifier,
        )
        Image(
            imageVector = Icons.Filled.Replay10,
            contentDescription = stringResource(R.string.cd_reply10),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(TextWhite),
            modifier = buttonsModifier
                .clickable {
                    rewind()
                }
        )
        Image(
            imageVector = Icons.Rounded.PlayCircleFilled,
            contentDescription = stringResource(R.string.cd_play),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(TextWhite),
            modifier = Modifier
                .size(playerButtonSize)
                .semantics { role = Role.Button }
                .clickable {
                    play()
                }
        )
        Image(
            imageVector = Icons.Filled.Forward30,
            contentDescription = stringResource(R.string.cd_forward30),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(TextWhite),
            modifier = buttonsModifier
                .clickable {
                    forward()
                }
        )
        Image(
            imageVector = Icons.Filled.SkipNext,
            contentDescription = stringResource(R.string.cd_skip_next),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(TextWhite),
            modifier = buttonsModifier
        )
    }
}
