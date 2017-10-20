package com.example.nanase.myminislide;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageSwitcher mImageSwitcher;
    int[] mImageResources = {R.drawable.owl0,R.drawable.owl1
            ,R.drawable.owl2,R.drawable.owl3
            ,R.drawable.owl4,R.drawable.owl5
            ,R.drawable.owl6,R.drawable.owl7
            ,R.drawable.owl8,R.drawable.owl9};
    int mPosition = 0;

    boolean mIsSlideshow = false;
    MediaPlayer mMediaPlayer;

    public class MainTimerTask extends TimerTask {
        @Override
        public void run(){
            if(mIsSlideshow){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        movePosition(1);
                    }
                });
            }
        }
    }

    Timer mTimer = new Timer();
    TimerTask mTimerTask = new MainTimerTask();
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                return imageView;
            }
        });
        mImageSwitcher.setImageResource(mImageResources[0]);

        mTimer.schedule(mTimerTask, 0, 3000);
        mMediaPlayer = MediaPlayer.create(this, R.raw.garacta);
        mMediaPlayer.setLooping(true);
    }

    private void movePosition(int move){
        mPosition = mPosition + move;
        if(mPosition >= mImageResources.length){
            mPosition = 0;
        }else if(mPosition < 0){
            mPosition = mImageResources.length - 1;
        }
        mImageSwitcher.setImageResource(mImageResources[mPosition]);
    }

    public void onStartButtonTapped(View view){
        mIsSlideshow = true;
        mMediaPlayer.start();
    }

    public void onStopButtonTapped(View view){
        mIsSlideshow = false;
        mMediaPlayer.pause();
        mMediaPlayer.seekTo(0);
    }
}
