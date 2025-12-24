package com.neolive.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _isStreaming = MutableLiveData(false)
    val isStreaming: LiveData<Boolean> = _isStreaming

    private val _selectedPlatform = MutableLiveData(PlatformPreset.YOUTUBE)
    val selectedPlatform: LiveData<PlatformPreset> = _selectedPlatform

    fun setPlatform(platform: PlatformPreset) {
        _selectedPlatform.value = platform
    }

    fun setStreaming(active: Boolean) {
        _isStreaming.value = active
    }
}

enum class PlatformPreset(val displayName: String, val rtmpBaseUrl: String) {
    YOUTUBE("YouTube", "rtmp://a.rtmp.youtube.com/live2"),
    TWITCH("Twitch", "rtmp://live.twitch.tv/app"),
    CUSTOM("Custom RTMP", ""),
}
