package com.yogi.data.repository

import com.yogi.data.firebase.FirebaseDatabaseInterface
import com.yogi.data.models.ChapterInfoItem
import com.yogi.data.util.NetworkUtility
import java.lang.Exception
import javax.inject.Inject
import com.yogi.data.core.Result
import com.yogi.data.models.ChapterDetailItem
import com.yogi.data.models.Slok
import com.yogi.data.entities.PreferredLanguage
import com.yogi.domain.localinfo.slokaNumberInChapterMap

class GitaGyanRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabaseInterface,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val networkUtility: NetworkUtility
) : GitaGyanRepository {
    override suspend fun getChapterList(): Result<List<ChapterInfoItem>> {
        return try {
            if(!networkUtility.isNetworkAvailable()) return Result.Error("No Internet")
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