package com.ynaito.servicesample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    LogService mService = null;
    boolean mBound = false;

    Button button1 = null;
    Button button2 = null;
    Button button3 = null;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            LogService.LocalBinder binder = (LogService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, "onServiceDisconnected");
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
        }
    };
    private String ACTION_NOTIFICATION_INTENT = "action_notification_intent";
    private int FLAG_NOTIFICATION_INTENT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick button1");
                Intent intent = new Intent(getApplicationContext(), LogService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                buildNotification();
            }
        });
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick button2");
//                Intent intent = new Intent(getApplicationContext(), LogService.class);
                unbindService(mConnection);
                deleteNotification();
            }
        });
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick button3");
                Intent intent = new Intent(getApplicationContext(), LogService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                buildNotification();
            }
        });


    }

    private void deleteNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        manager.cancel(100);
    }

    private void buildNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("TITLE iS XX");
        builder.setContentText("Text is XX");
        Intent seekToPreviousIntent = new Intent(this, LogService.class);
        seekToPreviousIntent.setAction(ACTION_NOTIFICATION_INTENT);
        PendingIntent seekToPreviousPendingIntent = PendingIntent.getService(getApplicationContext(), FLAG_NOTIFICATION_INTENT, seekToPreviousIntent, 0);
        builder.setContentIntent(seekToPreviousPendingIntent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            builder.addAction(R.mipmap.ic_launcher, "button", seekToPreviousPendingIntent);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            MediaSession mediaSession = new MediaSession(getApplicationContext(), "naito");
//            builder.setStyle(new Notification.MediaStyle()
//                    .setMediaSession(mediaSession.getSessionToken())
//                    .setShowActionsInCompactView(1));
//        }
        NotificationManager manager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            manager.notify(100, builder.build());
        }
    }

//    private PendingIntent getPendingIntentWithBroadcast(String action) {
//        return PendingIntent.getBroadcast(getApplicationContext(), 0 , new Intent(action), 0);
//    }
}
