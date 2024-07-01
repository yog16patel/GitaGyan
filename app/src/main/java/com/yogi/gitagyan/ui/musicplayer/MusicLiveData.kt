package com.yogi.gitagyan.ui.musicplayer

import androidx.lifecycle.MutableLiveData

val playerLiveData = MutableLiveData(PlayerStatus.STOP)



enum class PlayerStatus {
    PAUSED,
    RESUMED,
    STOP
}