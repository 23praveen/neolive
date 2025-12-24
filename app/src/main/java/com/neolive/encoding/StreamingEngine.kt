package com.neolive.encoding

import com.neolive.capture.AudioCapturer
import com.neolive.capture.CameraCapturer
import com.neolive.capture.ScreenCapturer
import com.neolive.overlay.StreamComposer
import com.neolive.service.StreamStartConfig
import com.neolive.streaming.RtmpStreamer

class StreamingEngine {
    private val screenCapturer = ScreenCapturer()
    private val audioCapturer = AudioCapturer()
    private var cameraCapturer: CameraCapturer? = null
    private val composer = StreamComposer()
    private val videoEncoder = VideoEncoder()
    private val audioEncoder = AudioEncoder()
    private val rtmpStreamer = RtmpStreamer()

    fun start(config: StreamStartConfig) {
        val (width, height) = parseResolution(config.resolution)
        composer.initialize()
        screenCapturer.start()
        if (config.enableCamera) {
            // Camera capturer requires a lifecycle owner and should be bound in Activity context.
        }
        audioCapturer.start()

        videoEncoder.prepare(width, height, config.fps, config.bitrateKbps)
        audioEncoder.prepare(sampleRate = 44100, bitrate = 128000, channels = 1)
        videoEncoder.start()
        audioEncoder.start()

        rtmpStreamer.connect(config.rtmpUrl)
    }

    fun stop() {
        rtmpStreamer.disconnect()
        videoEncoder.stop()
        audioEncoder.stop()
        audioCapturer.stop()
        screenCapturer.stop()
        composer.release()
        cameraCapturer?.stop()
    }

    private fun parseResolution(resolution: String): Pair<Int, Int> {
        val parts = resolution.split("x")
        return if (parts.size == 2) {
            parts[0].toIntOrNull() ?: 1280 to (parts[1].toIntOrNull() ?: 720)
        } else {
            1280 to 720
        }
    }
}
