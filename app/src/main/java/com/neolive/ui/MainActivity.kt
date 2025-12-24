package com.neolive.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.neolive.databinding.ActivityMainBinding
import com.neolive.service.StreamingService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var permissionHelper: PermissionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionHelper = PermissionHelper(this)
        setupSpinners()
        setupActions()
        observeState()
    }

    private fun setupSpinners() {
        val platformAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            PlatformPreset.values().map { it.displayName },
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.platformSpinner.adapter = platformAdapter

        val resolutionAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            ResolutionPreset.values().map { "${it.width}x${it.height} @${it.fps}fps" },
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.resolutionSpinner.adapter = resolutionAdapter

        val bitrateAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            BitratePreset.values().map { "${it.videoBitrateKbps} kbps" },
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.bitrateSpinner.adapter = bitrateAdapter
    }

    private fun setupActions() {
        binding.streamButton.setOnClickListener {
            if (!permissionHelper.hasPermissions()) {
                permissionHelper.requestPermissions(PERMISSIONS_REQUEST_CODE)
                return@setOnClickListener
            }

            val isStreaming = viewModel.isStreaming.value == true
            if (isStreaming) {
                stopStreaming()
            } else {
                startStreaming()
            }
        }
    }

    private fun observeState() {
        viewModel.isStreaming.observe(this) { streaming ->
            binding.streamButton.setText(
                if (streaming) com.neolive.R.string.stop_stream else com.neolive.R.string.start_stream,
            )
        }
    }

    private fun startStreaming() {
        val platform = PlatformPreset.values()[binding.platformSpinner.selectedItemPosition]
        val resolution = ResolutionPreset.values()[binding.resolutionSpinner.selectedItemPosition]
        val bitrate = BitratePreset.values()[binding.bitrateSpinner.selectedItemPosition]
        val streamKey = binding.streamKeyInput.text.toString()

        val rtmpUrl = if (platform == PlatformPreset.CUSTOM) {
            streamKey
        } else {
            "${platform.rtmpBaseUrl}/$streamKey"
        }

        val config = StreamConfig(
            rtmpUrl = rtmpUrl,
            streamKey = streamKey,
            resolution = resolution,
            bitrate = bitrate,
            enableCamera = binding.cameraToggle.isChecked,
        )

        val intent = Intent(this, StreamingService::class.java).apply {
            action = StreamingService.ACTION_START
            putExtra(StreamingService.EXTRA_RTMP_URL, config.rtmpUrl)
            putExtra(StreamingService.EXTRA_RESOLUTION, "${resolution.width}x${resolution.height}")
            putExtra(StreamingService.EXTRA_FPS, resolution.fps)
            putExtra(StreamingService.EXTRA_BITRATE_KBPS, bitrate.videoBitrateKbps)
            putExtra(StreamingService.EXTRA_ENABLE_CAMERA, config.enableCamera)
        }
        startForegroundService(intent)
        viewModel.setStreaming(true)
    }

    private fun stopStreaming() {
        val intent = Intent(this, StreamingService::class.java).apply {
            action = StreamingService.ACTION_STOP
        }
        startService(intent)
        viewModel.setStreaming(false)
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 2001
    }
}
