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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitaGyanViewModel @Inject constructor(
    private val getChapterListInteractor: GetChapterListInteractor,
    private val getSlokaDetailsInteractor: GetSlokaDetailsInteractor,
    private val sharedPreferencesRepository: SharedPreferencesRepository
): ViewModel() {

    private val _chapterListPageState = MutableStateFlow(ChapterListPageState())
    val chapterListPageState: StateFlow<ChapterListPageState>
        get() = _chapterListPageState

    private val _slokaDetailsPageState = MutableStateFlow(SlokaDetailsPageState())
    val slokaDetailsPageState: StateFlow<SlokaDetailsPageState>
        get() = _slokaDetailsPageState

    private val _languageState = MutableStateFlow(LanguageState())
    val languageState : StateFlow<LanguageState>
        get() = _languageState

    init {
        getChapterList()
    }

    private fun getChapterList() {
        viewModelScope.launch {
            when(val response = getChapterListInteractor.executeSync(Unit)){
                is Result.Success -> _chapterListPageState.value = ChapterListPageState(response.data)
                is Result.Error -> Log.e("Yogesh","ERROR: ${response.exception}")
            }
        }
    }

    fun getSlokaDetails(chapteNumber: Int){
        Log.e("Yogesh","selected chapter is : $chapteNumber")
        viewModelScope.launch {
            when(val response = getSlokaDetailsInteractor.executeSync(chapteNumber)){
                is Result.Success -> _slokaDetailsPageState.value = SlokaDetailsPageState(response.data)
                is Result.Error ->  Log.e("Yogesh","ERROR: ${response.exception}")
                else -> {
                    Log.e("Yogesh","ERROR: in else branch..")
                }
            }

        }
    }

    fun setLanguagePreferences(preferredLanguage: PreferredLanguage){
        _languageState.value = _languageState.value.copy(preferredLanguage = preferredLanguage)
        sharedPreferencesRepository.saveValueSharedPreferences("Language", preferredLanguage.languageCode)
        LanguageChangeUtil.applyLanguage(preferredLanguage.languageCode)
    }

    fun getLanguagePreference(){
        val savedLanguage = sharedPreferencesRepository.getSharedPreferencesValues("Language")
        savedLanguage?.let {
            val preferredLanguage = PreferredLanguage.valueToEnum(savedLanguage)
            _languageState.value.copy(preferredLanguage = preferredLanguage)
        }
    }

    fun getSetLanguageCode(): String = languageMap[_languageState.value.preferredLanguage] ?: "en"

}