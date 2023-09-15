package com.tshevchuk.prayer.presentation.audio;

import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.session.MediaSession;
import androidx.media3.session.MediaSessionService;

// Background playback with a MediaSessionService
// https://developer.android.com/guide/topics/media/session/mediasessionservice#java
public class AudioPlaybackService extends MediaSessionService {
    private MediaSession mediaSession = null;
    @Override
    public MediaSession onGetSession(MediaSession.ControllerInfo controllerInfo) {
        // If desired, validate the controller before returning the media session
        return mediaSession;
    }

    // Create your Player and MediaSession in the onCreate lifecycle event
    @Override
    public void onCreate() {
        super.onCreate();
        ExoPlayer player = new ExoPlayer.Builder(this).build();
        mediaSession = new MediaSession.Builder(this, player).build();
    }

    // Remember to release the player and media session in onDestroy
    @Override
    public void onDestroy() {
        mediaSession.getPlayer().release();
        mediaSession.release();
        mediaSession = null;
        super.onDestroy();
    }
}
