package com.yogi.gitagyan.ui.slokadetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogi.domain.core.GitaPair
import com.yogi.domain.core.Result
import com.yogi.domain.interactors.GetChapterListInteractor
import com.yogi.domain.interactors.GetSlokaDetailsInteractor
import com.yogi.gitagyan.LanguageState
import com.yogi.domain.interactors.GetNumberOfSlokaInteractor
import com.yogi.domain.interactors.SetLastReadSlokaAndChapterIndexValueInteractor
import com.yogi.domain.interactors.SetLastReadSlokaAndChapterNameInteractor
import com.yogi.domain.models.ChapterDetailItem
import com.yogi.domain.models.ChapterInfoItem
import com.yogi.gitagyan.base.PageState
import com.yogi.gitagyan.ui.chapterlist.ChapterListPageData
import com.yogi.gitagyan.ui.mappers.toChapterDetailItemUi
import com.yogi.gitagyan.ui.mappers.toChapterInfoItemUiList
import com.yogi.gitagyan.ui.util.GitaContentType
import com.yogi.gitagyan.ui.util.mapToSlokaIndexToAppSlokaNumber
import com.yogi.gitagyan.ui.util.mapToSlokaNumberWithTheAppSlokaIndex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _chapterListPageState =
        MutableStateFlow<PageState<ChapterListPageData>>(PageState.Loading)
    val chapterListPageState: StateFlow<PageState<ChapterListPageData>>
        get() = _chapterListPageState

    private val _slokaDetailsPageState = MutableStateFlow(SlokaDetailsPageState(isLoading = true))
    val slokaDetailsPageState: StateFlow<SlokaDetailsPageState>
        get() = _slokaDetailsPageState

    private val _selectedChapterState = MutableStateFlow(1)

    init {
        getChapterList()
    }

    private fun getChapterList() = viewModelScope.launch {
        _chapterListPageState.value = PageState.Loading
        processResult(getChapterListInteractor.executeSync(Unit), success = { data ->
            _chapterListPageState.value =
                PageState.Success(ChapterListPageData(chapterInfoItems = data.toChapterInfoItemUiList()))

        }, { exception ->
            _chapterListPageState.value = PageState.Error(exception)
        })
    }

    fun continueReading(chapterNumber: Int, slokNumber: Int) {
        slokaDetailsPageState.value.lastSelectedSloka = slokNumber
        updateSelectedChapter(chapterNumber)
    }

    fun updateSelectedChapter(chapterNumber: Int) = viewModelScope.launch {
        updateSlokList(chapterNumber)
        _selectedChapterState.value = chapterNumber
        _slokaDetailsPageState.value = _slokaDetailsPageState.value.copy(isLoading = true)

        processResult(
            result = getSlokaDetailsInteractor.executeSync(chapterNumber),
            success = { chapterDetailItem ->
                val chapterInfo = getChapterInfo(chapterNumber)
                updateSlokaDetailPageState(chapterDetailItem, chapterInfo)
            },
            error = {}
        )
    }

    private fun getChapterInfo(chapterNumber: Int): ChapterInfo {
        return when (val state = _chapterListPageState.value) {
            is PageState.Success -> {
                val chapter = state.data.chapterInfoItems.getOrNull(chapterNumber - 1)
                ChapterInfo(chapter?.chapterNumberTitle ?: "", chapter?.description ?: "")
            }

            else -> ChapterInfo("", "")
        }
    }

    private fun <T> processResult(
        result: Result<T?>?,
        success: (T) -> Unit,
        error: (String) -> Unit = {}
    ) {
        when (result) {
            is Result.Error -> { error(result.exception) }
            is Result.Success -> { result.data?.let { success(it) } }
            null -> error("")
        }
    }

    private fun updateSlokaDetailPageState(
        chapterDetailItem: ChapterDetailItem,
        chapterInfo: ChapterInfo
    ) {
        _slokaDetailsPageState.update {
            it.copy(
                isLoading = false,
                chapterDetailsItems = chapterDetailItem.toChapterDetailItemUi(
                    chapterInfo.title,
                    chapterInfo.description
                )
            )
        }
    }

    fun setLastSelectedSloka(
        slokaIndex: Int,
        contentType: GitaContentType = GitaContentType.SINGLE_PANE
    ) = viewModelScope.launch {
        _slokaDetailsPageState.value = _slokaDetailsPageState.value.copy(
                lastSelectedSloka = slokaIndex,
                isDetailOpen = contentType == GitaContentType.SINGLE_PANE
        )
        _slokaDetailsPageState.value.chapterDetailsItems?.let {
            val slokUi = it.slokUiEntityList[slokaIndex]
            val splitted = slokUi.slokaNumber.split(" ").getOrNull(1)
                ?:it.slokUiEntityList[slokaIndex].slokaNumber

            setLastReadSlokaAndChapterIndexName.executeSync(GitaPair(it.chapterTitle, splitted))
            setLastReadSlokaAndChapterIndexValueInteractor.executeSync(GitaPair(_selectedChapterState.value, slokaIndex))
        }
    }

    private fun updateSlokList(chapterNumber: Int) {
        viewModelScope.launch {
            val response = getNumberOfSlokaInteractor.executeSync(chapterNumber)
            _slokaDetailsPageState.value =
                slokaDetailsPageState.value.copy(totalSlokaList = response?.let {
                    (1..it).toList()
                } ?: emptyList())
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

    fun getLastSelectedSloka(selectedSlokNumber: Int): Int {
        val chapterInfo = _slokaDetailsPageState.value.chapterDetailsItems
        val list = chapterInfo?.slokUiEntityList ?: emptyList()
        return list.mapToSlokaIndexToAppSlokaNumber(selectedSlokNumber)
    }

    private data class ChapterInfo(val title: String, val description: String)
}