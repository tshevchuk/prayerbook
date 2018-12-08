package com.tshevchuk.prayer.presentation.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tshevchuk.prayer.R;

public class AudioControlIconButton extends FrameLayout {
    private String audioUrl;
    private boolean paused = false;

    private ImageButton ibPlay;
    private ImageButton ibPause;
    private ProgressBar pbLoading;
    private final AudioBroadcastReceiver audioBroadcastReceiver = new AudioBroadcastReceiver();
    private final AudioButtonClickListener audioButtonClickListener = new AudioButtonClickListener();
    private String audioTitle;
    private int audioStartPosition;

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
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(
                audioBroadcastReceiver,
                new IntentFilter(AudioPlayerService.LOCAL_BROADCAST_ACTION
        ));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(audioBroadcastReceiver);
    }

    public void setAudio(String audioUrl, int startPosition, String title) {
        if(!TextUtils.isEmpty(this.audioUrl)  || TextUtils.isEmpty(audioUrl)){
            return;
        }
        this.audioUrl = audioUrl;
        audioTitle = title;
        audioStartPosition = startPosition;
        setVisibility(VISIBLE);

        final Intent intent = new Intent(getContext(), AudioPlayerService.class);
        intent.setAction(AudioPlayerService.ACTION_RESEND_LOCAL_BROADCAST);
        getContext().startService(intent);
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

    private class AudioBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(!intent.getStringExtra(AudioPlayerService.LOCAL_BROADCAST_PARAM_URL)
                    .equals(audioUrl)
            ){
                return;
            }

            AudioPlayerService.LocalBroadcastState state = (AudioPlayerService.LocalBroadcastState)
                    intent.getSerializableExtra(AudioPlayerService.LOCAL_BROADCAST_PARAM_STATE);
            switch(state){
                case Error:
                    Toast.makeText(getContext(), "Помилка відтворення аудіо файлу",
                            Toast.LENGTH_LONG
                    ).show();
                    ibPlay.setVisibility(VISIBLE);
                    ibPause.setVisibility(INVISIBLE);
                    pbLoading.setVisibility(INVISIBLE);
                    paused = false;
                    break;
                case Pause:
                    ibPlay.setVisibility(VISIBLE);
                    ibPause.setVisibility(INVISIBLE);
                    pbLoading.setVisibility(INVISIBLE);
                    paused = true;
                    break;
                case Play:
                    ibPlay.setVisibility(INVISIBLE);
                    ibPause.setVisibility(VISIBLE);
                    pbLoading.setVisibility(INVISIBLE);
                    break;
                case Prepare:
                    ibPlay.setVisibility(INVISIBLE);
                    ibPause.setVisibility(INVISIBLE);
                    pbLoading.setVisibility(VISIBLE);
                    break;
                case Stop:
                    ibPlay.setVisibility(VISIBLE);
                    ibPause.setVisibility(INVISIBLE);
                    pbLoading.setVisibility(INVISIBLE);
                    paused = false;
                    break;
            }
        }
    }

    private class AudioButtonClickListener implements ImageButton.OnClickListener{
        @Override
        public void onClick(View v) {
            final Context context = getContext();
            final Intent intent = new Intent(context, AudioPlayerService.class);
            intent.setData(Uri.parse(audioUrl));
            intent.putExtra(AudioPlayerService.PARAM_TITLE, audioTitle);
            intent.putExtra(AudioPlayerService.PARAM_AUDIO_START_POSITION, audioStartPosition);
            if(v == ibPause){
                intent.setAction(AudioPlayerService.ACTION_PAUSE);
            }else if(v == ibPlay){
                if(paused){
                    intent.setAction(AudioPlayerService.ACTION_RESUME);
                }else{
                    intent.setAction(AudioPlayerService.ACTION_START);
                }
            }
            context.startService(intent);
        }
    }
}
