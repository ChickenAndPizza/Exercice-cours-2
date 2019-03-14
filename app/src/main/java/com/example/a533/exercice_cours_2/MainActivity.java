package com.example.a533.exercice_cours_2;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this,R.raw.farm);
        mediaPlayer.setLooping(false);

        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ShowAlert("Audio terminé");
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SeekBar seekbar = findViewById(R.id.sbMusic);
                seekbar.setProgress(mediaPlayer.getCurrentPosition()*100/mediaPlayer.getDuration());
                handler.postDelayed(this, 1000);
            }
        }, 1000);
        setOnClickListener();
    }

    public void setOnClickListener()
    {
        findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMediaPlayer();
            }
        });
        findViewById(R.id.btnPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseMediaPlayer();
            }
        });

        setMusicListener();
        setSoundListener();

    }

    private void playMediaPlayer()
    {
        if (!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }

    private void pauseMediaPlayer()
    {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    private void setMusicListener()
    {
        SeekBar seekBar = findViewById(R.id.sbMusic);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    seekInMedia(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void seekInMedia(int progress)
    {
        mediaPlayer.seekTo(progress*mediaPlayer.getDuration()/100);
    }

    private void setSoundListener()
    {
        SeekBar seekBar = findViewById(R.id.sbSound);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    seekSound(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void seekSound(int progress)
    {
        float log1= 1 - ((float)(Math.log(15-(progress*15/100))/Math.log(15)));
        mediaPlayer.setVolume(log1,log1);
    }

    private void ShowAlert(String message) {
        Toast.makeText(((Activity) this), message,
                Toast.LENGTH_LONG).show();
    }
}
