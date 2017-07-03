package com.ynaito.servicesample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by y.naito on 2017/07/03.
 */

public class FooReceiver extends BroadcastReceiver {
    private String TAG = FooReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive intent : " + intent);

    }
}
