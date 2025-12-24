package com.neolive.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.neolive.encoding.StreamingEngine

class StreamingService : Service() {
    private lateinit var notificationHelper: StreamingNotificationHelper
    private val streamingEngine = StreamingEngine()

    override fun onCreate() {
        super.onCreate()
        notificationHelper = StreamingNotificationHelper(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startStreaming(intent)
            ACTION_STOP -> stopStreaming()
        }
        return START_STICKY
    }

    private fun startStreaming(intent: Intent) {
        val notification: Notification = notificationHelper.buildStreamingNotification()
        startForeground(NOTIFICATION_ID, notification)

        val rtmpUrl = intent.getStringExtra(EXTRA_RTMP_URL).orEmpty()
        val resolution = intent.getStringExtra(EXTRA_RESOLUTION).orEmpty()
        val fps = intent.getIntExtra(EXTRA_FPS, 30)
        val bitrate = intent.getIntExtra(EXTRA_BITRATE_KBPS, 4000)
        val enableCamera = intent.getBooleanExtra(EXTRA_ENABLE_CAMERA, false)

        streamingEngine.start(
            StreamStartConfig(
                rtmpUrl = rtmpUrl,
                resolution = resolution,
                fps = fps,
                bitrateKbps = bitrate,
                enableCamera = enableCamera,
            ),
        )
    }

    private fun stopStreaming() {
        streamingEngine.stop()
        stopForeground(STOP_FOREGROUND_DETACH)
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val ACTION_START = "com.neolive.action.START"
        const val ACTION_STOP = "com.neolive.action.STOP"

        const val EXTRA_RTMP_URL = "extra_rtmp_url"
        const val EXTRA_RESOLUTION = "extra_resolution"
        const val EXTRA_FPS = "extra_fps"
        const val EXTRA_BITRATE_KBPS = "extra_bitrate_kbps"
        const val EXTRA_ENABLE_CAMERA = "extra_enable_camera"

        private const val NOTIFICATION_ID = 200
    }
}
