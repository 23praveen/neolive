package com.neolive.capture

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder

class AudioCapturer {
    private var audioRecord: AudioRecord? = null

    fun start() {
        val sampleRate = 44100
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize,
        ).apply {
            startRecording()
        }
    }

    fun stop() {
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }
}
