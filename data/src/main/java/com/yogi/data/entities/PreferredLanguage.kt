package com.yogi.data.entities


enum class PreferredLanguage(val languageName: String, val languageCode: String, val index: Int) {
    ENGLISH(languageName = "English", languageCode = "en", index = 0),
    HINDI(languageName = "Hindi", languageCode = "hi", index = 1);

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
        fun languageNameToEnum(languageName: String?): PreferredLanguage {
            return values().find {
                it.languageName == languageName
            } ?: ENGLISH
        }
    }
}
