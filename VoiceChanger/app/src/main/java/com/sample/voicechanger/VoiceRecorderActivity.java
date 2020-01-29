package com.sample.voicechanger;


import android.Manifest;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bins.audioeffects.AudioFile;
import com.bins.audioeffects.FFMpegCallback;
import com.bins.audioeffects.FFMpegMediaConverter;

import java.io.IOException;

public class VoiceRecorderActivity extends AppCompatActivity {

    private MediaPlayer player = null;
    private String LOG_TAG = "AudioRecordTest";
    private int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private String fileName = "";
    private MediaPlayer playerBackground = null;
    private MediaPlayer playerEffects = null;
    private String backgroundMusic = "";
    private MediaRecorder recorder = null;
    private CountDownTimer countDownTimer = null;
    private String outFile = "";

    Button btRecord;
    ImageView ivNext;
    TextView tvTimer;


    private void startT() {
        player = new MediaPlayer();
        try {
            player.setDataSource(outFile);
            player.prepare();
            player.start();
            countDownTimer = new CountDown(31000, 1000);
            countDownTimer.start();
        } catch (IOException e) {
        }
    }

    private String[] permissions =
            new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recorder);

        // Record to the external cache directory for visibility
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audioRecord.mp3";


        btRecord = findViewById(R.id.btRecord);
        ivNext = findViewById(R.id.ivNext);
        tvTimer = findViewById(R.id.tvTimer);


        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

    }


    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btRecord:
                //this to show how to merge two effects in a file -
//                File clap = UtilKt.getFileFromAssets(this,"clap.mp3");
//                File yeehaw = UtilKt.getFileFromAssets(this,"yeehaw.mp3");
//                File hay = UtilKt.getFileFromAssets(this,"hey.mp3");
//                outFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/outfile21.mp3";
//
//
//
//                new FFMpegMediaConverter(new FFMpegCallback() {
//                    @Override
//                    public void onSuccess(String[] commands) {
//                        startT();
//                    }
//
//                    @Override
//                    public void onCancel(String[] commands) {
//
//                    }
//
//                    @Override
//                    public void onFailed(String[] commands, int rc, String out) {
//
//                    }
//                }).mergeFiles(hay.getAbsolutePath(),clap.getAbsolutePath(),5000,yeehaw.getAbsolutePath(),10000,outFile);

                Button btRecord = findViewById(R.id.btRecord);
                if (btRecord.getText() == getString(R.string.record)) {
                    if (!backgroundMusic.isEmpty()) {
                        playBackgroundMusic(backgroundMusic);
                    }
                    startRecording();
                } else {
                    btRecord.setText(R.string.record);
                    countDownTimer.cancel();
                    stopRecording();
                }

                break;

            case R.id.btClaps:
                playEffects("clap.mp3");
                break;

            case R.id.btYeehaw:
                playEffects("yeehaw.mp3");

                break;

            case R.id.btDrops:
                playEffects("waterdrops.mp3");
                break;

            case R.id.btCreative:
                backgroundMusic = "creative.mp3";
                break;

            case R.id.btHey:
                backgroundMusic = "hey.mp3";
                break;

            case R.id.btIdea:
                backgroundMusic = "idea.mp3";
                break;

            case R.id.ivNext:

//                val filePath1 =  getFileFromAssets(this, "hey.mp3").absolutePath
//                val filePath =  getFileFromAssets(this, "clap.mp3").absolutePath
//                new FFMpegMediaConverter(this).mergeFiles(filePath1,filePath,3000,outFile)

                startActivity(new Intent(this, EffectsActivity.class));

                break;

        }
    }


    public void showArrayMerge(View view){
        findViewById(R.id.progress_circular).setVisibility(View.VISIBLE);
        AudioFile clap = new AudioFile(UtilKt.getFileFromAssets(this,"clap.mp3").getAbsolutePath(),5000);
        AudioFile yeehaw = new AudioFile(UtilKt.getFileFromAssets(this,"yeehaw.mp3").getAbsolutePath(),10000);
        AudioFile source = new AudioFile(UtilKt.getFileFromAssets(this,"hey.mp3").getAbsolutePath(),0);
        AudioFile[] effects = new AudioFile[]{
                clap,yeehaw
        };
        outFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/outfile21.mp3";



        new FFMpegMediaConverter(new FFMpegCallback() {
            @Override
            public void onSuccess(String[] commands) {
                findViewById(R.id.progress_circular).setVisibility(View.GONE);
                startT();
            }

            @Override
            public void onCancel(String[] commands) {
                Toast.makeText(VoiceRecorderActivity.this,"Cancelld ",Toast.LENGTH_LONG).show();
                findViewById(R.id.progress_circular).setVisibility(View.GONE);
            }

            @Override
            public void onFailed(String[] commands, int rc, String out) {
                Toast.makeText(VoiceRecorderActivity.this,"onFailed ",Toast.LENGTH_LONG).show();
                findViewById(R.id.progress_circular).setVisibility(View.GONE);

            }
        }).mergeFiles(source,outFile,effects);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
        if (playerBackground != null) {
            playerBackground.release();
        }
        if (playerEffects != null) {
            playerEffects.release();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        playerBackground = null;
        playerEffects = null;
    }

    /**
     * Function used to stop the recording
     */
    void stopRecording() {
        ivNext.setVisibility(View.VISIBLE);
        if(playerEffects != null){
            playerEffects.stop();
        }
        if(playerBackground != null) {
            playerBackground.stop();
        }
        if(recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    /**
     * Function used to play effects
     */
    private void playEffects(String fileName) {

        if (playerEffects != null) {
            playerEffects.stop();
            playerEffects.release();
        }

        playerEffects = new MediaPlayer();
        try {
            AssetFileDescriptor descriptor = getAssets().openFd(fileName);
            makeAudioStreamMaxVolume();
            playerEffects.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            playerEffects.prepare();
            playerEffects.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    /**
     * Function used to play background music
     */
    private void playBackgroundMusic(String fileName) {

        if (playerBackground != null) {
            playerBackground.stop();
            playerBackground.release();
        }

        playerBackground = new MediaPlayer();
        try {
            AssetFileDescriptor descriptor = getAssets().openFd(fileName);
            makeAudioStreamMaxVolume();
            playerBackground.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            playerBackground.prepare();
            playerBackground.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }


    /**
     * Function used to start the recordings
     */
    public void startRecording() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
        ) == -1 ||
                ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == -1 ||
                ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == -1
        ) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
            return;
        }

        ivNext.setVisibility(View.GONE);
        btRecord.setText(R.string.stop);
        tvTimer.setText(R.string._30);
        countDownTimer = new CountDown(31000, 1000);
        countDownTimer.start();

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }


    }

    /**
     * Function to make background music and effects to play in max volume
     */
    private void makeAudioStreamMaxVolume() {
        try {
            AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (mAudioManager != null) {
                mAudioManager.setStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                        0
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvTimer.setText((millisUntilFinished / 1000) + "");
            if (tvTimer.getText() == "1") {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            btRecord.performClick();
                            tvTimer.setText(R.string._30);
                        }
                    }
                }, 1000);

            }
        }

        @Override
        public void onFinish() {

        }
    }


}
