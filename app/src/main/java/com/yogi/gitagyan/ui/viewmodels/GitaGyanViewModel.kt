package com.yogi.gitagyan.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogi.domain.core.GitaPair
import com.yogi.domain.core.Result
import com.yogi.domain.interactors.GetChapterListInteractor
import com.yogi.domain.interactors.GetSlokaDetailsInteractor
import com.yogi.domain.repository.SharedPreferencesRepository
import com.yogi.gitagyan.LanguageState
import com.yogi.gitagyan.ui.slokadetails.SlokaDetailsPageState
import com.yogi.gitagyan.ui.chapterlist.ChapterListPageState
import com.yogi.domain.entities.PreferredLanguage
import com.yogi.domain.interactors.GetCurrentStateInteractor
import com.yogi.domain.interactors.GetNumberOfSlokaInteractor
import com.yogi.domain.interactors.QODInteractor
import com.yogi.domain.interactors.SetLastReadSlokaAndChapterIndexValueInteractor
import com.yogi.domain.interactors.SetLastReadSlokaAndChapterNameInteractor
import com.yogi.gitagyan.LanguageChangeUtil
import com.yogi.gitagyan.ui.CurrentState
import com.yogi.gitagyan.ui.mappers.toChapterDetailItemUi
import com.yogi.gitagyan.ui.mappers.toChapterInfoItemUiList
import com.yogi.gitagyan.ui.util.GitaContentType
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
    private val getCurrentState: GetCurrentStateInteractor,
    private val getNumberOfSlokaInteractor: GetNumberOfSlokaInteractor,
    private val qodInteractor: QODInteractor,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val setLastReadSlokaAndChapterIndexName: SetLastReadSlokaAndChapterNameInteractor,
    private val setLastReadSlokaAndChapterIndexValueInteractor: SetLastReadSlokaAndChapterIndexValueInteractor
) : ViewModel() {

    private val _chapterListPageState = MutableStateFlow(ChapterListPageState(isLoading = true))
    val chapterListPageState: StateFlow<ChapterListPageState>
        get() = _chapterListPageState

    private val _slokaDetailsPageState = MutableStateFlow(SlokaDetailsPageState(isLoading = true))
    val slokaDetailsPageState: StateFlow<SlokaDetailsPageState>
        get() = _slokaDetailsPageState

    private val _qodState = MutableStateFlow<String?>("")
    val qodState: StateFlow<String?>
        get() = _qodState

    private val _currentState = MutableStateFlow(CurrentState())
    val currentState: StateFlow<CurrentState>
        get() = _currentState

    private val _languageState = MutableStateFlow(LanguageState())
    val languageState: StateFlow<LanguageState>
        get() = _languageState

    var isContinueReading: Boolean = false
        private set


    init {
        getQOD()
        getCurrentProgress()
        getLanguagePreference()
        getChapterList()
    }

    private fun getQOD() {
        viewModelScope.launch {
            _qodState.value = qodInteractor.executeSync(Unit)
        }
    }


    private fun getCurrentProgress() {
        viewModelScope.launch {
            val response = getCurrentState.executeSync(Unit)
            _currentState.value = CurrentState(
                selectedChapterString = response.selectedChapterString,
                lastSlokString = response.lastSlokString,
                selectedChapterIndex = response.selectedChapterIndex,
                selectedSlokIndex = response.selectedSlokIndex,
                currentProgress = response.currentProgress,
                likedSloka = 0
            )
        }
    }

    private fun getChapterList() {
        _chapterListPageState.update {
            it.copy(
                isLoading = true,
                chapterInfoItems = emptyList()
            )
        }
        viewModelScope.launch {
            when (val response = getChapterListInteractor.executeSync(Unit)) {
                is Result.Success -> {
                    _chapterListPageState.update {
                        it.copy(
                            chapterInfoItems = response.data.toChapterInfoItemUiList(),
                            isLoading = false
                        )
                    }
                }

                is Result.Error -> Log.e("Yogesh", "ERROR: ${response.exception}")
            }
        }
    }

    fun exploreGita() {
        isContinueReading = false
    }

    fun continueReading(chapterNumber: Int, slokNumber: Int) {
        isContinueReading = true
        slokaDetailsPageState.value.lastSelectedSloka = slokNumber
        updateSelectedChapter(chapterNumber)
    }

    fun updateSelectedChapter(chapterNumber: Int) {
        updateSlokList(chapterNumber)
        chapterListPageState.value.selectedChapter = chapterNumber
        _slokaDetailsPageState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val response = getSlokaDetailsInteractor.executeSync(chapterNumber)) {
                is Result.Success -> {
                    _slokaDetailsPageState.update {
                        val description =
                            chapterListPageState.value.chapterInfoItems[chapterNumber - 1].description
                        val title =
                            chapterListPageState.value.chapterInfoItems[chapterNumber - 1].translation
                        it.copy(
                            isLoading = false,
                            chapterDetailsItems = response.data?.toChapterDetailItemUi(
                                title,
                                description
                            )
                        )
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
        LanguageChangeUtil.applyLanguage(preferredLanguage.languageCode)
        _languageState.value = _languageState.value.copy(preferredLanguage = preferredLanguage)
        sharedPreferencesRepository.saveLanguageToSharedPref(preferredLanguage)
        getChapterList()
    }

    fun getLanguagePreference() {
        val savedLanguage = sharedPreferencesRepository.getLanguageFromSharedPref()
        savedLanguage?.let {
            _languageState.value = _languageState.value.copy(preferredLanguage = savedLanguage)
        }
    }

    fun setLastSelectedSloka(slokNumber: Int, contentType: GitaContentType, slokaIndex: Int) {
        _slokaDetailsPageState.value =
            _slokaDetailsPageState.value.copy(
                lastSelectedSloka = slokNumber,
                isDetailOpen = contentType == GitaContentType.SINGLE_PANE
            )
        viewModelScope.launch {
            _slokaDetailsPageState.value.chapterDetailsItems?.run {
                val splitted = slokUiEntityList[slokaIndex].slokaNumber.split(" ")

                setLastReadSlokaAndChapterIndexName.executeSync(
                    GitaPair(
                        chapterTitle,
                        if (splitted.size >= 2) splitted[1] else slokUiEntityList[slokaIndex].slokaNumber
                    )
                )
                setLastReadSlokaAndChapterIndexValueInteractor.executeSync(
                    GitaPair(
                        chapterListPageState.value.selectedChapter,
                        slokaIndex
                    )
                )
                getCurrentProgress()
            }
        }
    }

    private fun updateSlokList(chapterNumber: Int) {
        viewModelScope.launch {
            val response = getNumberOfSlokaInteractor.executeSync(chapterNumber)
            _slokaDetailsPageState.value =
                slokaDetailsPageState.value.copy(totalSlokaList = response?.let {
                    (1..it).toList()
                } ?: emptyList()
                )
        }
    }

    fun closeDetailScreen() {
        _slokaDetailsPageState.value = _slokaDetailsPageState.value
            .copy(
                isDetailOpen = false,
                lastSelectedSloka = _slokaDetailsPageState.value.lastSelectedSloka
            )
    }

    fun openDetailScreen() {
        _slokaDetailsPageState.value = _slokaDetailsPageState.value
            .copy(
                isDetailOpen = true,
                lastSelectedSloka = _slokaDetailsPageState.value.lastSelectedSloka
            )
    }

}