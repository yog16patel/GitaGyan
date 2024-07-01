package com.yogi.gitagyan.ui.musicplayer

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.yogi.gitagyan.R
import com.yogi.gitagyan.ui.MainActivity

class MediaPlayerService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private val ACTION_PLAY: String = "com.example.action.PLAY"
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var notification: Notification


    //    private val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
//    private val wifiLock: WifiManager.WifiLock =
//        wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock")
    private val mBinder: IBinder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        val filter = IntentFilter(PLAY_EVENT)

        val receiver = MyBroadcastReceiver()
        registerReceiver(receiver, filter)
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = NotificationManagerCompat.from(this)
        createNotificationChannel()
        startPlayer()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                TIMER_SERVICE_NOTIFICATION_CHANNEL_ID,
                getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(serviceChannel)
        }
    }

    private fun startPlayer(): Int {
        @SuppressLint("RemoteViewLayout")
        mediaPlayer = MediaPlayer().apply {
            setOnPreparedListener(this@MediaPlayerService)
            setOnErrorListener(this@MediaPlayerService)
            setWakeMode(this@MediaPlayerService, PowerManager.PARTIAL_WAKE_LOCK)

        }
        return START_STICKY
    }

    override fun onPrepared(mp: MediaPlayer?) {
        onMediaPlayPause()
        //wifiLock.acquire()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        mp?.reset()
        return true
    }

    fun playMedia(sourceUrl: String) {
        val notificationLayoutExpanded =
            RemoteViews(packageName, R.layout.music_player_notification)

        val intent = Intent(this, MediaPlayerService::class.java).apply {
            action = PLAY_EVENT
        }

        val pendingIntent: PendingIntent = PendingIntent.getService(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        notificationLayoutExpanded.setOnClickPendingIntent(R.id.previous_song, pendingIntent)
        notificationLayoutExpanded.setOnClickPendingIntent(R.id.pause_song, pendingIntent)
        notificationLayoutExpanded.setOnClickPendingIntent(R.id.next_song, pendingIntent)

        mediaPlayer?.apply {
            setDataSource(sourceUrl)
            prepareAsync()
        }
        notification = NotificationCompat
            .Builder(this, TIMER_SERVICE_NOTIFICATION_CHANNEL_ID)
            .setCustomContentView(notificationLayoutExpanded)
            .setSmallIcon(R.drawable.ic_music)
            .setOngoing(true)
            .build()
        startForeground(TIMER_SERVICE_NOTIFICATION_ID, notification)
    }

    fun onMediaPlayPause(
        playing: (Boolean)->Unit = {}
    ) {
        if(mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
            playing(true)
        }
        else{
            mediaPlayer?.pause()
            playing(false)
        }
    }

    fun forward(){
        mediaPlayer?.let {
            mediaPlayer?.seekTo(it.currentPosition + 30000)
        }
    }

    fun rewind(){
        mediaPlayer?.let {
            mediaPlayer?.seekTo(it.currentPosition - 10000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        //wifiLock.release()
    }

    inner class LocalBinder : Binder() {
        val service: MediaPlayerService
            get() = this@MediaPlayerService

    }

    companion object {
        const val TIMER_SERVICE_NOTIFICATION_CHANNEL_ID = "TimerServiceChannel"
        const val TIMER_SERVICE_NOTIFICATION_ID = 69
    }
}