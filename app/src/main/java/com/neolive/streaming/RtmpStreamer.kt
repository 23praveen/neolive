package com.neolive.streaming

class RtmpStreamer {
    fun connect(rtmpUrl: String) {
        // TODO: Establish RTMP handshake and connect.
    }

    fun sendVideo(data: ByteArray, timestampMs: Long) {
        // TODO: Push H.264 NAL units into FLV muxer.
    }

    fun sendAudio(data: ByteArray, timestampMs: Long) {
        // TODO: Push AAC frames into FLV muxer.
    }

    fun disconnect() {
        // TODO: Close RTMP session.
    }
}
