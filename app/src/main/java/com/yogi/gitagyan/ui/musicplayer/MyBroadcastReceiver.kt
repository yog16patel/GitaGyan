package com.yogi.gitagyan.ui.musicplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("Yogesh","Event clicked....1")

        if (PLAY_EVENT.equals(intent?.action)) {
            Log.e("Yogesh","Event clicked....2")
        }
    }
}
val PLAY_EVENT:String = "yogi.gitagyan.musicplayer.play"