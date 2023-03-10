package com.yogi.gitagyan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.ktx.Firebase


sealed class Screen(val route: String) {
    object ChapterList : Screen("chapterlist")
    object ChapterDetails : Screen("chapter_details/{chapter_number}") {
        fun createRoute(chapter_number: String) = "chapter_details/$chapter_number"
    }
}

@Composable
fun rememberGitaGyanAppState(
    navHostController: NavHostController = rememberNavController()
) = remember(navHostController) {
    GitaGyanAppState(navHostController)
}

class GitaGyanAppState(
    val navController: NavHostController
) {
    
    fun navigateToChapterDetails(chapterNumber: String, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate(Screen.ChapterDetails.createRoute(chapterNumber))
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

