package com.neolive.streaming

class FlvMuxer {
    fun writeVideoPacket(data: ByteArray, timestampMs: Long): ByteArray {
        // TODO: Wrap H.264 into FLV video tag.
        return data
    }

    fun writeAudioPacket(data: ByteArray, timestampMs: Long): ByteArray {
        // TODO: Wrap AAC into FLV audio tag.
        return data
    }
}
