package com.yogi.gitagyan.ui.slokadetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogi.domain.core.GitaPair
import com.yogi.domain.core.Result
import com.yogi.domain.interactors.GetChapterListInteractor
import com.yogi.domain.interactors.GetSlokaDetailsInteractor
import com.yogi.gitagyan.LanguageState
import com.yogi.gitagyan.ui.chapterlist.ChapterListPageState
import com.yogi.domain.interactors.GetNumberOfSlokaInteractor
import com.yogi.domain.interactors.SetLastReadSlokaAndChapterIndexValueInteractor
import com.yogi.domain.interactors.SetLastReadSlokaAndChapterNameInteractor
import com.yogi.gitagyan.ui.mappers.toChapterDetailItemUi
import com.yogi.gitagyan.ui.mappers.toChapterInfoItemUiList
import com.yogi.gitagyan.ui.util.GitaContentType
import com.yogi.gitagyan.ui.util.mapToSlokaIndexToAppSlokaNumber
import com.yogi.gitagyan.ui.util.mapToSlokaNumberWithTheAppSlokaIndex
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
    private val getNumberOfSlokaInteractor: GetNumberOfSlokaInteractor,
    private val setLastReadSlokaAndChapterIndexName: SetLastReadSlokaAndChapterNameInteractor,
    private val setLastReadSlokaAndChapterIndexValueInteractor: SetLastReadSlokaAndChapterIndexValueInteractor
) : ViewModel() {
    
    private val _languageState = MutableStateFlow(LanguageState())
    val languageState: StateFlow<LanguageState>
        get() = _languageState
    private val _chapterListPageState = MutableStateFlow(ChapterListPageState(isLoading = true))
    val chapterListPageState: StateFlow<ChapterListPageState>
        get() = _chapterListPageState

    private val _slokaDetailsPageState = MutableStateFlow(SlokaDetailsPageState(isLoading = true))
    val slokaDetailsPageState: StateFlow<SlokaDetailsPageState>
        get() = _slokaDetailsPageState

    init {
        getChapterList()
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

    fun continueReading(chapterNumber: Int, slokNumber: Int) {
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
    fun setLastSelectedSloka(slokaIndex: Int, contentType: GitaContentType = GitaContentType.SINGLE_PANE ) {
        _slokaDetailsPageState.value =
            _slokaDetailsPageState.value.copy(
                lastSelectedSloka = slokaIndex,
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

    fun goToSelectedSloka(index: Int) {
        val chapterInfo = _slokaDetailsPageState.value.chapterDetailsItems
        val list = chapterInfo?.slokUiEntityList ?: emptyList()
        setLastSelectedSloka(list.mapToSlokaNumberWithTheAppSlokaIndex(index))
    }

    fun getLastSelectedSloka(selectedSlokNumber: Int) : Int{
        val chapterInfo = _slokaDetailsPageState.value.chapterDetailsItems
        val list = chapterInfo?.slokUiEntityList ?: emptyList()
        return list.mapToSlokaIndexToAppSlokaNumber(selectedSlokNumber)
    }


}