package com.yogi.gitagyan.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogi.domain.core.Result
import com.yogi.domain.interactors.GetChapterListInteractor
import com.yogi.domain.interactors.GetSlokaDetailsInteractor
import com.yogi.domain.repository.SharedPreferencesRepository
import com.yogi.gitagyan.LanguageState
import com.yogi.gitagyan.appconfig.LanguageChangeUtil
import com.yogi.gitagyan.appconfig.languageMap
import com.yogi.gitagyan.ui.slokadetails.SlokaDetailsPageState
import com.yogi.gitagyan.ui.chapterlist.ChapterListPageState
import com.yogi.gitagyan.ui.model.PreferredLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitaGyanViewModel @Inject constructor(
    private val getChapterListInteractor: GetChapterListInteractor,
    private val getSlokaDetailsInteractor: GetSlokaDetailsInteractor,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    private val _chapterListPageState = MutableStateFlow(ChapterListPageState(isLoading = true))
    val chapterListPageState: StateFlow<ChapterListPageState>
        get() = _chapterListPageState

    private val _slokaDetailsPageState = MutableStateFlow(SlokaDetailsPageState(isLoading = true))
    val slokaDetailsPageState: StateFlow<SlokaDetailsPageState>
        get() = _slokaDetailsPageState

    private val _languageState = MutableStateFlow(LanguageState())
    val languageState: StateFlow<LanguageState>
        get() = _languageState

    init {
        getChapterList()
    }

    private fun getChapterList() {
        _chapterListPageState.update {
            it.copy(
                isLoading = false
            )
        }
        viewModelScope.launch {
            when (val response = getChapterListInteractor.executeSync(Unit)) {
                is Result.Success -> {
                    _chapterListPageState.update {
                        it.copy(
                            chapterInfoItems = response.data,
                            isLoading = false
                        )
                    }
                }

                is Result.Error -> Log.e("Yogesh", "ERROR: ${response.exception}")
            }
        }
    }

    fun getSlokaDetails(chapterNumber: Int) {
        _slokaDetailsPageState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val response = getSlokaDetailsInteractor.executeSync(chapterNumber)) {
                is Result.Success -> {
                    _slokaDetailsPageState.update {
                        it.copy(isLoading = false, chapterInfoItems = response.data)
                    }
                }

                is Result.Error -> Log.e("Yogesh", "ERROR: ${response.exception}")
                else -> {
                    Log.e("Yogesh", "ERROR: in else branch..")
                }
            }

        }
    }

    fun setLanguagePreferences(preferredLanguage: PreferredLanguage) {
        _languageState.value = _languageState.value.copy(preferredLanguage = preferredLanguage)
        sharedPreferencesRepository.saveValueSharedPreferences(
            "Language",
            preferredLanguage.languageCode
        )
        LanguageChangeUtil.applyLanguage(preferredLanguage.languageCode)
    }

}