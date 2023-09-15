package com.tshevchuk.prayer.presentation.audio;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.session.MediaController;
import androidx.media3.session.SessionToken;

import com.danikula.videocache.HttpProxyCacheServer;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.R;

import java.util.Objects;

public class AudioControlIconButton extends FrameLayout {
    private String audioUrl;
    private boolean paused = false;

    private ImageButton ibPlay;
    private ImageButton ibPause;
    private ProgressBar pbLoading;
    private final AudioButtonClickListener audioButtonClickListener = new AudioButtonClickListener();

    ListenableFuture<MediaController> controllerFuture;

    MediaController mediaController;

    private final HttpProxyCacheServer audioHttpProxy
            = PrayerBookApplication.getInstance().getAudioHttpProxy();

    private String audioTitle;

    public AudioControlIconButton(Context context) {
        super(context);
        init(null, 0);
    }

    public AudioControlIconButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public AudioControlIconButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.v_audio_control_icon_button, this);
        ibPlay = findViewById(R.id.ibPlay);
        ibPause = findViewById(R.id.ibPause);
        pbLoading = findViewById(R.id.pbLoading);
        ibPlay.setOnClickListener(audioButtonClickListener);
        ibPause.setOnClickListener(audioButtonClickListener);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!TextUtils.isEmpty(audioUrl)) {
            initializeMediaController();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        releaseMediaController();
    }

    public void setAudio(String audioUrl, int startPosition, String title) {
        if(!TextUtils.isEmpty(this.audioUrl)  || TextUtils.isEmpty(audioUrl)){
            return;
        }
        this.audioUrl = audioUrl;
        audioTitle = title;
        setVisibility(VISIBLE);

        initializeMediaController();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putString("audioUrl", audioUrl);
        bundle.putBoolean("paused", paused);
        bundle.putString("audioTitle", audioTitle);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            audioUrl = bundle.getString("audioUrl");
            paused = bundle.getBoolean("paused");
            audioTitle = bundle.getString("audioTitle");
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }

    private void initializeMediaController() {
        if (controllerFuture != null) {
            return;
        }

        SessionToken sessionToken =
            new SessionToken(
                getContext(), new ComponentName(getContext(), AudioPlaybackService.class)
            );

        controllerFuture = new MediaController.Builder(getContext(), sessionToken).buildAsync();
        controllerFuture.addListener(
                new MediaControllerFutureListener(),
                MoreExecutors.directExecutor()
        );
    }

    private void releaseMediaController() {
        if (controllerFuture != null) {
            MediaController.releaseFuture(controllerFuture);
            controllerFuture = null;
        }

        mediaController = null;
    }

    private void showMediaPlayerErrorMessage(String errorMessage) {
        Toast.makeText(getContext(), errorMessage,
                Toast.LENGTH_LONG
        ).show();

        ibPlay.setVisibility(mediaController != null ? VISIBLE : INVISIBLE);
        ibPause.setVisibility(INVISIBLE);
        pbLoading.setVisibility(INVISIBLE);
        paused = false;
    }

    private void onPlayEnded() {
        ibPlay.setVisibility(VISIBLE);
        ibPause.setVisibility(INVISIBLE);
        pbLoading.setVisibility(INVISIBLE);
        paused = false;
    }

    private void onPlayPaused() {
        ibPlay.setVisibility(VISIBLE);
        ibPause.setVisibility(INVISIBLE);
        pbLoading.setVisibility(INVISIBLE);
        paused = true;
    }

    private void onPlayStarted() {
        ibPlay.setVisibility(INVISIBLE);
        ibPause.setVisibility(VISIBLE);
        pbLoading.setVisibility(INVISIBLE);
    }

    private void showProgressBar() {
        ibPlay.setVisibility(INVISIBLE);
        ibPause.setVisibility(INVISIBLE);
        pbLoading.setVisibility(VISIBLE);
    }

    private boolean isSameAudio() {
        return Objects.requireNonNull(mediaController.getCurrentMediaItem()).mediaId
                .equals(audioUrl);
    }

    private class AudioButtonClickListener implements ImageButton.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v == ibPause){
                if (
                    mediaController.isPlaying()
                        && mediaController.isCommandAvailable(Player.COMMAND_PLAY_PAUSE)
                ) {
                    mediaController.pause();
                }
                ibPlay.setVisibility(VISIBLE);
                ibPause.setVisibility(INVISIBLE);
                pbLoading.setVisibility(INVISIBLE);
                paused = true;
            } else if (v == ibPlay) {
                if (paused) {
                    // resume
                    if (mediaController.isCommandAvailable(Player.COMMAND_PLAY_PAUSE)) {
                        mediaController.play();
                    }
                    ibPlay.setVisibility(INVISIBLE);
                    ibPause.setVisibility(VISIBLE);
                    pbLoading.setVisibility(INVISIBLE);
                    paused = false;
                } else {
                    // start

                    MediaMetadata metadata = new MediaMetadata.Builder()
                            .setArtist(getContext().getString(R.string.app_name))
                            .setTitle(audioTitle)
                            .build();
                    MediaItem mediaItem =
                        new MediaItem.Builder()
                            .setUri(audioHttpProxy.getProxyUrl(audioUrl))
                            .setMediaId(audioUrl)
                            .setMediaMetadata(metadata)
                            .build();
                    mediaController.setMediaItem(mediaItem);
                    mediaController.prepare();
                    mediaController.play();

                    showProgressBar();
                }
            }
        }
    }

    private class MediaControllerFutureListener implements Runnable {
        @Override
        public void run() {
            try {
                mediaController = controllerFuture.get();
                mediaController.addListener(new MyListener());

                if (
                    mediaController.isPlaying() && isSameAudio()
                ) {
                    ibPlay.setVisibility(INVISIBLE);
                    ibPause.setVisibility(VISIBLE);
                    pbLoading.setVisibility(INVISIBLE);
                }
            } catch (Exception e) {
                showMediaPlayerErrorMessage(e.getLocalizedMessage());
            }
        }
    }

    private class MyListener implements Player.Listener {
        @Override
        public void onPlayerError(PlaybackException error) {
            showMediaPlayerErrorMessage(error.getLocalizedMessage());
        }

        @Override
        public void onPlaybackStateChanged(int playbackState) {
            if (playbackState == Player.STATE_ENDED) {
                onPlayEnded();
            }
        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            if (isPlaying) {
                onPlayStarted();
            } else if (mediaController.getPlaybackState() == Player.STATE_READY && isSameAudio()) {
                onPlayPaused();
            }
        }
    }
}
