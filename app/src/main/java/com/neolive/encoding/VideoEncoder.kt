package com.neolive.encoding

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat

class VideoEncoder {
    private var mediaCodec: MediaCodec? = null

    fun prepare(width: Int, height: Int, fps: Int, bitrateKbps: Int) {
        val format = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height).apply {
            setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
            setInteger(MediaFormat.KEY_BIT_RATE, bitrateKbps * 1000)
            setInteger(MediaFormat.KEY_FRAME_RATE, fps)
            setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 2)
        }

        mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC).apply {
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
