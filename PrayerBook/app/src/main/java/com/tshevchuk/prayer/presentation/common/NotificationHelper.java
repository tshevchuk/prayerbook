package com.tshevchuk.prayer.presentation.common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.presentation.audio.AudioPlayerService;
import com.tshevchuk.prayer.presentation.home.HomeActivity;

public class NotificationHelper {
    public enum AudioPlayButton {Resume, Pause}

    @SuppressWarnings("FieldCanBeLocal")
    private final int AUDIO_NOTIFICATION_ID = 1;

    public void showAudioPlayNotification(Service service, AudioPlayButton audioPlayButton,
                                          Uri audioUri, String audioTitle) {
        final Context context = service.getApplicationContext();
        Intent intent = new Intent(context.getApplicationContext(), HomeActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            notificationManager.createNotificationChannel(channel);
        }

        Intent stopServiceIntent = new Intent(context, AudioPlayerService.class);
        stopServiceIntent.setAction(AudioPlayerService.ACTION_STOP_SERVICE);
        PendingIntent stopServicePendingIntent
                = PendingIntent.getService(context, 0, stopServiceIntent, 0);
        NotificationCompat.Builder notifBuilder
                = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(audioTitle)
                .setContentIntent(pi)
                .setDeleteIntent(stopServicePendingIntent);

        if (audioPlayButton == AudioPlayButton.Resume) {
            Intent resumeIntent = new Intent(context, AudioPlayerService.class);
            resumeIntent.setAction(AudioPlayerService.ACTION_RESUME);
            resumeIntent.setData(audioUri);
            PendingIntent resumePendingIntent = PendingIntent.getService(context, 0,
                    resumeIntent, 0);
            notifBuilder.addAction(R.drawable.ic_play_arrow_black_24dp, "Resume",
                    resumePendingIntent);
        } else if (audioPlayButton == AudioPlayButton.Pause) {
            Intent pauseIntent = new Intent(context, AudioPlayerService.class);
            pauseIntent.setAction(AudioPlayerService.ACTION_PAUSE);
            pauseIntent.setData(audioUri);
            PendingIntent pausePendingIntent = PendingIntent.getService(context, 0,
                    pauseIntent, 0);
            notifBuilder.addAction(R.drawable.ic_pause_black_24dp, "Pause",
                    pausePendingIntent);
        }

        Notification notif = notifBuilder
                .setStyle(
                        new android.support.v4.media.app.NotificationCompat.MediaStyle()
                                .setCancelButtonIntent(stopServicePendingIntent)
                                .setShowActionsInCompactView(0)
                                .setShowCancelButton(true)
                )
                .build();

        notificationManager.notify(AUDIO_NOTIFICATION_ID, notif);

        if(audioPlayButton == AudioPlayButton.Pause) {
            service.startForeground(AUDIO_NOTIFICATION_ID, notif);
        } else{
            service.stopForeground(false);
        }
    }

    public void hideAudioPlayNotification(Service service){
        service.stopForeground(true);
        NotificationManager notificationManager =
                (NotificationManager) service.getApplicationContext()
                        .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AUDIO_NOTIFICATION_ID);
    }
}
