package com.yogi.gitagyan.base

sealed class PageState<out T> {
    object Loading: PageState<Nothing>()
    data class Success<T>(val data: T): PageState<T>()
    data class Error(val errorMessage: String) : PageState<Nothing>()
}