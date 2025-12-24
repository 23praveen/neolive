package com.neolive.capture

import android.media.projection.MediaProjection

class ScreenCapturer {
    var mediaProjection: MediaProjection? = null
        private set

    fun attachProjection(projection: MediaProjection) {
        mediaProjection = projection
    }

    fun start() {
        // TODO: Initialize VirtualDisplay + Surface for MediaProjection.
    }

    fun stop() {
        // TODO: Release VirtualDisplay resources.
    }
}
