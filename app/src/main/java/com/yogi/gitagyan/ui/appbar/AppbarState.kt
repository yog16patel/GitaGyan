package com.yogi.gitagyan.ui.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class AppbarState(
    var title: String = "",
    var showBackButton: Boolean = true,
    var showActionButton: Boolean = false,
    var action: (@Composable RowScope.()->Unit)? = null
)
