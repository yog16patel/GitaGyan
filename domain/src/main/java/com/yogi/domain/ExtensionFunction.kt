package com.yogi.domain

import com.yogi.domain.core.GitaPair

fun String?.toPair(): GitaPair<String, String> {
    val index = this?.indexOf(",")?: return defaultGitaPair
    if(index < 0) return defaultGitaPair

    val first = this.substring(0, index-1)
    val second = this.substring(index + 1, this.length)

    return if (second.isBlank() || first.isBlank()) defaultGitaPair
    else GitaPair(first, second)

}

fun String?.toPairInt(): GitaPair<Int, Int> {
    val list = this?.split(",")
    return if (list.isNullOrEmpty() || list.size < 2) GitaPair(1, 1)
    else {
        val first = list[0].replace(" ","").toInt()
        val second = list[1].replace(" ","").toInt()
        GitaPair(first, second)
    }
}

private val defaultGitaPair = GitaPair("1","1")