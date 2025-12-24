package com.neolive.capture

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

class CameraCapturer(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
) {
    private var cameraProvider: ProcessCameraProvider? = null
    private var selector: CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    fun start() {
        val providerFuture = ProcessCameraProvider.getInstance(context)
        providerFuture.addListener(
            {
                cameraProvider = providerFuture.get()
                val preview = Preview.Builder().build()
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(lifecycleOwner, selector, preview)
            },
            ContextCompat.getMainExecutor(context),
        )
    }

    fun switchCamera() {
        selector = if (selector == CameraSelector.DEFAULT_FRONT_CAMERA) {
            CameraSelector.DEFAULT_BACK_CAMERA
        } else {
            CameraSelector.DEFAULT_FRONT_CAMERA
        }
        start()
    }

    fun stop() {
        cameraProvider?.unbindAll()
    }
}
