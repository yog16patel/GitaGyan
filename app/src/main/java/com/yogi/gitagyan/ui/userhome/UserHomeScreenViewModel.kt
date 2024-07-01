package com.yogi.gitagyan.ui.userhome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogi.domain.entities.PreferredLanguage
import com.yogi.domain.interactors.GetCurrentStateInteractor
import com.yogi.domain.interactors.QODInteractor
import com.yogi.domain.repository.SharedPreferencesRepository
import com.yogi.gitagyan.LanguageChangeUtil
import com.yogi.gitagyan.LanguageState
import com.yogi.gitagyan.ui.CurrentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserHomeScreenViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val qodInteractor: QODInteractor,
    private val getCurrentState: GetCurrentStateInteractor,

    ): ViewModel() {
    private val _qodState = MutableStateFlow<String?>("")
    val qodState: StateFlow<String?>
        get() = _qodState
    private val _currentState = MutableStateFlow(CurrentState())
    val currentState: StateFlow<CurrentState>
        get() = _currentState

    private val _languageState = MutableStateFlow(LanguageState())
    val languageState: StateFlow<LanguageState>
        get() = _languageState

    fun loadFreshData(){
        getQOD()
        fetchCurrentProgress()
        getLanguagePreference()
    }
    private fun getQOD() {
        viewModelScope.launch {
            _qodState.value = qodInteractor.executeSync(Unit)
        }
    }
    private fun fetchCurrentProgress() {
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

    fun setLanguagePreferences(preferredLanguage: PreferredLanguage) {
        LanguageChangeUtil.applyLanguage(preferredLanguage.languageCode)
        _languageState.value = _languageState.value.copy(preferredLanguage = preferredLanguage)
        sharedPreferencesRepository.saveLanguageToSharedPref(preferredLanguage)
    }

    fun getLanguagePreference() {
        val savedLanguage = sharedPreferencesRepository.getLanguageFromSharedPref()
        savedLanguage?.let {
            _languageState.value = _languageState.value.copy(preferredLanguage = savedLanguage)
        }
    }

}