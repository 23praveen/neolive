package com.neolive.overlay

import android.opengl.GLES20

class StreamComposer {
    fun initialize() {
        // TODO: Setup EGL context and input surfaces for screen + camera.
    }

    fun renderFrame() {
        // TODO: Use OpenGL to composite screen background + facecam + watermark.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
    }

    fun release() {
        // TODO: Release EGL context and textures.
    }
}
