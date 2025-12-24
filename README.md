# NeoLive

NeoLive is a Kotlin-based Android MVP for streaming device screens and optional facecam overlays to RTMP endpoints (YouTube, Twitch, or custom RTMP URLs). This project focuses on a clean, modular architecture suitable for Play Store delivery and indie developer maintenance.

## Features

- Screen capture via **MediaProjection** (API 26+)
- Optional **CameraX** facecam overlay
- Microphone audio capture via **AudioRecord**
- Hardware-accelerated H.264/AAC encoding via **MediaCodec**
- RTMP streaming layer (FLV muxing stubbed for integration)
- Foreground service with persistent notification
- Floating overlay controls (start/stop, mute, switch camera)

## Architecture Overview

```
com.neolive
├── capture/    // Screen, camera, microphone capture
├── encoding/   // MediaCodec encoders + streaming engine
├── streaming/  // RTMP client + FLV muxer stubs
├── overlay/    // OpenGL compositor + floating controls
├── service/    // Foreground streaming service
└── ui/         // Activities and ViewModels
```

## Setup

1. Open the project in **Android Studio Hedgehog (or newer)**.
2. Sync Gradle dependencies.
3. Run on a device with **Android 8.0 (API 26)+**.
4. Start a test RTMP endpoint:
   - YouTube: `rtmp://a.rtmp.youtube.com/live2/<stream-key>`
   - Twitch: `rtmp://live.twitch.tv/app/<stream-key>`
   - Custom: provide full RTMP URL in the stream key input.

## Streaming Pipeline (MVP)

1. **Capture Layer** collects screen/camera/mic signals.
2. **Composition Layer** uses OpenGL ES to blend layers.
3. **Encoding Layer** outputs H.264 + AAC via MediaCodec.
4. **Streaming Layer** muxes to FLV and pushes RTMP.

> NOTE: RTMP muxing and socket transport are stubbed and should be replaced with a production RTMP stack.

## Permissions & Compliance

Runtime permissions required:

- `RECORD_AUDIO`
- `CAMERA`
- `INTERNET`
- Foreground service declaration
- MediaProjection consent dialog (to be launched during screen capture start)

## Next Steps

- Implement MediaProjection consent flow
- Build OpenGL compositor for screen + facecam + watermark
- Implement FLV muxing and RTMP transport
- Add bitrate auto-adjustment and thermal monitoring

## Sample RTMP Configuration

```
RTMP URL: rtmp://a.rtmp.youtube.com/live2
Stream Key: your-key-here
```

## License

MIT (replace with your desired license)
