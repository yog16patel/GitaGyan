package com.yogi.gitagyan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object UserHomeScreen: Screen("user_home_screen")
    object ChapterList : Screen("chapter_list_screen")
    object ChapterOverview : Screen("chapter_overview") {
        val withNavItems = "$route/{chNumber}/{slockNumber}"
    }
    object ChapterDetails : Screen("chapter_details")
    object MusicPlayer : Screen("music_player")
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

    fun navigateToUserHome(from: NavBackStackEntry,builder: NavOptionsBuilder.() -> Unit = {  } ){
        if(from.lifecycleIsResumed()){
            navController.navigate(Screen.UserHomeScreen.route){
                builder.invoke(this)
            }
        }
    }

    fun navigateToChapterList(from: NavBackStackEntry,builder: NavOptionsBuilder.() -> Unit = {  } ){
        if(from.lifecycleIsResumed()){
            navController.navigate(Screen.ChapterList.route,){
                builder.invoke(this)
            }
        }
    }

    fun navigateChapterOverview(from: NavBackStackEntry, chNumber: Int, slockNumber: Int){
        if(from.lifecycleIsResumed()) {
            navController.navigate("${Screen.ChapterOverview.route}/$chNumber/$slockNumber")
        }
    }

    fun navigateMusicPlayer(from: NavBackStackEntry){
        if(from.lifecycleIsResumed()){
            navController.navigate(Screen.MusicPlayer.route)
        }

    }

    fun navigateToChapterDetails(from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate(Screen.ChapterDetails.route)
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

