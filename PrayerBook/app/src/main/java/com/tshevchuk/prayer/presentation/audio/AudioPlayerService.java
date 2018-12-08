package com.tshevchuk.prayer.presentation.audio;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;

import com.danikula.videocache.HttpProxyCacheServer;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.common.NotificationHelper;

import java.io.IOException;

public class AudioPlayerService extends Service{
    public enum LocalBroadcastState {Prepare, Play, Pause, Stop, Error}

    public static final String ACTION_START = "com.tshevchuk.prayer.audioservice.action.START";
    public static final String ACTION_PAUSE = "com.tshevchuk.prayer.audioservice.action.PAUSE";
    public static final String ACTION_RESUME = "com.tshevchuk.prayer.audioservice.action.RESUME";
    public static final String ACTION_STOP_SERVICE
            = "com.tshevchuk.prayer.audioservice.action.STOP_SERVICE";
    public static final String ACTION_RESEND_LOCAL_BROADCAST
            = "com.tshevchuk.prayer.audioservice.action.ACTION_RESEND_LOCAL_BROADCAST";
    public static final String PARAM_TITLE = "com.tshevchuk.prayer.audioservice.param.TITLE";
    public static final String PARAM_AUDIO_START_POSITION
            = "com.tshevchuk.prayer.audioservice.param.AUDIO_START_POSITION";
    public static final String LOCAL_BROADCAST_ACTION
            = "com.tshevchuk.prayer.audioservice.broadcast";
    public static final String LOCAL_BROADCAST_PARAM_STATE
            = "com.tshevchuk.prayer.audioservice.param_state";
    public static final String LOCAL_BROADCAST_PARAM_URL
            = "com.tshevchuk.prayer.audioservice.param_url";

    private MediaPlayer mediaPlayer = null;
    private Uri currentDataUri;
    private Intent lastLocalBroadcastIntent;
    private String audioTitle;
    private int audioStartPosition;
    private final HttpProxyCacheServer audioHttpProxy
            = PrayerBookApplication.getInstance().getAudioHttpProxy();
    private final NotificationHelper notificationHelper = new NotificationHelper();


    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ACTION_START.equals(intent.getAction())) {
            if(mediaPlayer != null){
                mediaPlayer.release();
                sendLocalBroadcastEvent(LocalBroadcastState.Stop, currentDataUri);
            }
            currentDataUri = intent.getData();
            audioTitle = intent.getStringExtra(PARAM_TITLE);
            audioStartPosition = intent.getIntExtra(PARAM_AUDIO_START_POSITION, 0);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(new OnPreparedListener(currentDataUri));
            mediaPlayer.setOnErrorListener(new OnErrorListener(startId));
            mediaPlayer.setOnCompletionListener(new OnCompletionListener(startId));
            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                final String proxyUrl  = audioHttpProxy.getProxyUrl(currentDataUri.toString());
                mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(proxyUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.prepareAsync();
            sendLocalBroadcastEvent(LocalBroadcastState.Prepare, currentDataUri);
        } else if(ACTION_PAUSE.equals(intent.getAction())){
            if(currentDataUri != null && currentDataUri.equals(intent.getData())){
                mediaPlayer.pause();
                notificationHelper.showAudioPlayNotification(
                    this, NotificationHelper.AudioPlayButton.Resume,
                        intent.getData(), audioTitle
                );
                sendLocalBroadcastEvent(LocalBroadcastState.Pause, intent.getData());
            }
        } else if(ACTION_RESUME.equals(intent.getAction())){
            if(currentDataUri != null && currentDataUri.equals(intent.getData())){
                mediaPlayer.start();
                notificationHelper.showAudioPlayNotification(
                        this, NotificationHelper.AudioPlayButton.Pause,
                        intent.getData(), audioTitle
                );
                sendLocalBroadcastEvent(LocalBroadcastState.Play, intent.getData());
            }
        } else if(ACTION_STOP_SERVICE.equals(intent.getAction())){
            stopSelf();
        } else if(ACTION_RESEND_LOCAL_BROADCAST.equals(intent.getAction())){
            if(lastLocalBroadcastIntent != null) {
                LocalBroadcastManager.getInstance(getApplicationContext())
                        .sendBroadcast(lastLocalBroadcastIntent);
            }
        }

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.release();
            sendLocalBroadcastEvent(LocalBroadcastState.Stop, currentDataUri);
        }
        notificationHelper.hideAudioPlayNotification(this);
    }

    private void sendLocalBroadcastEvent(LocalBroadcastState state, Uri audioUri){
        Intent intent = new Intent(LOCAL_BROADCAST_ACTION);
        intent.putExtra(LOCAL_BROADCAST_PARAM_STATE, state);
        intent.putExtra(LOCAL_BROADCAST_PARAM_URL, audioUri.toString());
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        lastLocalBroadcastIntent = intent;
    }

    private class OnPreparedListener implements MediaPlayer.OnPreparedListener{
        private final Uri dataUri ;

        OnPreparedListener(Uri dataUri){
            this.dataUri = dataUri;
        }

        @Override
        public void onPrepared(MediaPlayer player) {
            if(audioStartPosition > 0) {
            //    player.seekTo(audioStartPosition);
            }
            player.start();
            notificationHelper.showAudioPlayNotification(
                    AudioPlayerService.this, NotificationHelper.AudioPlayButton.Pause, dataUri,
                    audioTitle
            );
            sendLocalBroadcastEvent(LocalBroadcastState.Play, dataUri);
        }
    }

    private class OnErrorListener implements MediaPlayer.OnErrorListener{
        private int startId;

        OnErrorListener(int startId){
            this.startId = startId;
        }
        @Override
        public boolean onError(MediaPlayer player, int what, int extra) {
            sendLocalBroadcastEvent(LocalBroadcastState.Error, currentDataUri);
            AudioPlayerService.this.stopSelf(startId);
            return false;
        }
    }

    private class OnCompletionListener implements MediaPlayer.OnCompletionListener{
        private int startId;

        OnCompletionListener(int startId){
            this.startId = startId;
        }

        @Override
        public void onCompletion(MediaPlayer player) {
            sendLocalBroadcastEvent(LocalBroadcastState.Stop, currentDataUri);
            AudioPlayerService.this.stopSelf(startId);
        }
    }
}
