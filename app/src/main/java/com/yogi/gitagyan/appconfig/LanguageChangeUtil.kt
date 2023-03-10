package com.yogi.gitagyan.appconfig

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.yogi.gitagyan.ui.model.PreferredLanguage

object LanguageChangeUtil {
    fun applyLanguage(
        languageId: String
    ) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(
                languageId
            )
        )

    }
}

val languageMap = mapOf(
    PreferredLanguage.ENGLISH to "en",
    PreferredLanguage.HINDI to "hi"
)