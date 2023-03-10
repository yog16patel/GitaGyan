package com.yogi.domain.model

data class Sloka(
    val isFavorite: Boolean = false,
    val number: String = "1",
    val sanskrit: String = "",
    val translations: Translations = Translations.NO_OP
)