package com.neolive.overlay

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import com.neolive.R

class FloatingControls(private val context: Context) {
    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var containerView: View? = null

    fun show(onStop: () -> Unit, onMute: () -> Unit, onSwitchCamera: () -> Unit) {
        if (containerView != null) return

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT,
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 100
            y = 200
        }

        val view = LayoutInflater.from(context).inflate(R.layout.overlay_controls, null)
        view.findViewById<ImageButton>(R.id.stopButton).setOnClickListener { onStop() }
        view.findViewById<ImageButton>(R.id.muteButton).setOnClickListener { onMute() }
        view.findViewById<ImageButton>(R.id.switchCameraButton).setOnClickListener { onSwitchCamera() }

        view.setOnTouchListener(DragTouchListener(params))
        windowManager.addView(view, params)
        containerView = view
    }

    fun hide() {
        containerView?.let { windowManager.removeView(it) }
        containerView = null
    }

    private inner class DragTouchListener(
        private val params: WindowManager.LayoutParams,
    ) : View.OnTouchListener {
        private var lastX = 0
        private var lastY = 0

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.rawX.toInt()
                    lastY = event.rawY.toInt()
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.rawX.toInt() - lastX
                    val deltaY = event.rawY.toInt() - lastY
                    params.x += deltaX
                    params.y += deltaY
                    windowManager.updateViewLayout(view, params)
                    lastX = event.rawX.toInt()
                    lastY = event.rawY.toInt()
                    return true
                }
            }
            return false
        }
    }
}
