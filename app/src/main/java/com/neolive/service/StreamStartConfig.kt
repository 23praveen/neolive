package com.neolive.service

data class StreamStartConfig(
    val rtmpUrl: String,
    val resolution: String,
    val fps: Int,
    val bitrateKbps: Int,
    val enableCamera: Boolean,
)
