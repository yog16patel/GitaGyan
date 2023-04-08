package com.yogi.data.repository

import com.yogi.domain.core.Result
import com.yogi.domain.firebase.FirebaseDatabaseInterface
import com.yogi.domain.entities.PreferredLanguage
import com.yogi.domain.localinfo.slokaNumberInChapterMap
import com.yogi.domain.models.ChapterDetailItem
import com.yogi.domain.models.ChapterInfoItem
import com.yogi.domain.models.Slok
import com.yogi.domain.repository.GitaGyanRepository
import com.yogi.domain.repository.SharedPreferencesRepository
import java.lang.Exception
import javax.inject.Inject

class GitaGyanRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabaseInterface,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : GitaGyanRepository {
    override suspend fun getChapterList(): Result<List<ChapterInfoItem>> {
        return try {
            val lang = sharedPreferencesRepository.getLanguageFromSharedPref()?.languageName   ?: PreferredLanguage.ENGLISH.languageName
            val response = firebaseDatabase.getChapterList(
                language = lang,
            ) ?: emptyList()

            val chapterInfoItemList = response.map {
                ChapterInfoItem(
                    chapterNumber = it.chapterNumber,
                    chapterNumberTitle = it.chapterNumberTitle,
                    description = it.description,
                    language = it.language,
                    name = it.name,
                    translation = it.translation
                )
            }
            Result.Success(chapterInfoItemList)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Exception")
        }
    }

    override suspend fun getChapterDetails(chapterNumber: String): Result<ChapterDetailItem?>? {
        return try {
            val response = firebaseDatabase.getSlokasOfChapter(
                language = sharedPreferencesRepository.getLanguageFromSharedPref()?.languageName
                    ?: PreferredLanguage.ENGLISH.languageName,
                chapterNumber = chapterNumber
            )
            val mappedResponse = response?.let {
                ChapterDetailItem(
                    chapterNumber = it.chapterNumber,
                    slokEntityList = it.slokList.map { slok ->
                        Slok(
                            slokaNumber = slok.slokaNumber,
                            slokaSanskrit = slok.slokaSanskrit,
                            slokaTranslation = slok.slokaTranslation
                        )
                    }
                )
            }
            Result.Success(mappedResponse)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Exception")
        }
    }

    override suspend fun getNumberOfSloka(chapterNumber: Int): Int? {
        return slokaNumberInChapterMap[chapterNumber]
    }

}