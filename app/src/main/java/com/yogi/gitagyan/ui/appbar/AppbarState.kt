package com.yogi.gitagyan.ui.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class AppbarState(
    var title: String = "",
    var action: (@Composable RowScope.()->Unit)? = null
)
