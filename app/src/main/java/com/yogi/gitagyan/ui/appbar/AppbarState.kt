package com.yogi.gitagyan.ui.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class AppbarState(
    val title: String = "",
    val action: (@Composable RowScope.()->Unit)? = null
)
