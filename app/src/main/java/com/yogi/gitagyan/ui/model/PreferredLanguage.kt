package com.yogi.gitagyan.ui.model


enum class PreferredLanguage(val languageCode: String, val index: Int) {
    ENGLISH("en",0),
    HINDI("hi",1);

    companion object {
        private val eumWithIndexMap = mapOf(
            0 to ENGLISH,
            1 to HINDI
        )

        fun indexToEnum(index: Int): PreferredLanguage = eumWithIndexMap[index] ?: ENGLISH
        fun valueToEnum(value: String): PreferredLanguage {
            return values().find {
                it.languageCode == value
            } ?: ENGLISH
        }
    }
}
