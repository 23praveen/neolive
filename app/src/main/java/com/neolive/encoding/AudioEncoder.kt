package com.neolive.encoding

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat

class AudioEncoder {
    private var mediaCodec: MediaCodec? = null

    fun prepare(sampleRate: Int, bitrate: Int, channels: Int) {
        val format = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC, sampleRate, channels).apply {
            setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC)
            setInteger(MediaFormat.KEY_BIT_RATE, bitrate)
            setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 16384)
        }

        mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC).apply {
            configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        }
    }

    fun start() {
        mediaCodec?.start()
    }

    fun stop() {
        mediaCodec?.stop()
        mediaCodec?.release()
        mediaCodec = null
    }
}
