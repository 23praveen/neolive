package com.neolive.ui

data class StreamConfig(
    val rtmpUrl: String,
    val streamKey: String,
    val resolution: ResolutionPreset,
    val bitrate: BitratePreset,
    val enableCamera: Boolean,
    val enableMic: Boolean = true,
    val enableScreen: Boolean = true,
)

enum class ResolutionPreset(val width: Int, val height: Int, val fps: Int) {
    HD_720(1280, 720, 30),
    HD_1080(1920, 1080, 30),
    SD_540(960, 540, 30),
}

enum class BitratePreset(val videoBitrateKbps: Int) {
    LOW(2000),
    MEDIUM(4000),
    HIGH(6000),
}
