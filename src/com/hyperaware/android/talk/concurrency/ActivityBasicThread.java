package com.hyperaware.android.talk.concurrency;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

public class ActivityBasicThread extends Activity {

    private static final String LOG_TAG = ActivityBasicThread.class.getSimpleName();

    private TextView tvContent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

        new BasicThread(R.string.content_loaded).start();
    }

    private void initViews() {
        setContentView(R.layout.activity_async_load);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }


    private class BasicThread extends Thread {
        private final int resId;

        public BasicThread(final int res_id) {
            this.resId = res_id;
        }

        @Override
        public void run() {
            Log.d(LOG_TAG, Thread.currentThread().getName() + " Loading content in 5 seconds");
            SystemClock.sleep(5000);
            Log.d(LOG_TAG, Thread.currentThread().getName() + " Content loaded");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvContent.setText(resId);
                    Log.d(LOG_TAG, Thread.currentThread().getName() + " Content loaded");
                }
            });
        }
    }

}
