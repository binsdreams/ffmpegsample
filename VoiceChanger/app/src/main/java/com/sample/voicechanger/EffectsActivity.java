package com.sample.voicechanger;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bins.audioeffects.Effects;
import com.bins.audioeffects.FFMpegCallback;
import com.bins.audioeffects.FFMpegMediaConverter;
import com.bins.audioeffects.Options;

import java.io.IOException;

public class EffectsActivity extends AppCompatActivity implements FFMpegCallback, View.OnClickListener {

    private String LOG_TAG = EffectsActivity.class.getName();
    private String fileName = "";
    private String fileNameNew = "";

    private MediaPlayer player = null;
    private FFMpegMediaConverter mediaConverter = null;

    /**
     * Function to start the Media Player
     */
    private void start() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileNameNew);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }


    /**
     * Function used to play the audio like a Radio
     */
    private void playRadio() {
        showProgress();
        if (player != null) {
            player.stop();
        }
        Options options = new Options.Builder(Effects.RADIO, fileName, fileNameNew).build();
        mediaConverter.execute(options);
    }


    /**
     * Function used to play the audio like a Chipmunk
     */
    private void playChipmunk() {
        showProgress();
        if (player != null) {
            player.stop();
        }
        Options options = new Options.Builder(Effects.CHIPMUNCK, fileName, fileNameNew).build();
        mediaConverter.execute(options);

    }

    /**
     * Function used to play the audio like a Robot
     */
    private void playRobot() {
        showProgress();
        if (player != null) {
            player.stop();
        }
        Options options = new Options.Builder(Effects.ROBOT, fileName, fileNameNew).build();
        mediaConverter.execute(options);
    }

    /**
     * Function used to play the audio like a Cave
     */
    private void playCave() {
        showProgress();
        if (player != null) {
            player.stop();
        }
        Options options = new Options.Builder(Effects.CAVE, fileName, fileNameNew).build();
        mediaConverter.execute(options);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ivBack:
                onBackPressed();
                break;

            case R.id.ivChipmunk:
                playChipmunk();
                break;

            case R.id.ivRobot:
                playRobot();
                break;

            case R.id.ivRadio:
                playRadio();
                break;

            case R.id.ivCave:
                playCave();
                break;
        }

    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_effects);
        mediaConverter = new FFMpegMediaConverter(this);
        // Record to the external cache directory for visibility
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audioRecord.mp3";
        fileNameNew = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audioRecordNew.mp3";
    }

    @Override
    public void onStop() {
        super.onStop();
        player.release();
        player = null;
    }


    private void showProgress() {
        findViewById(R.id.progress_circular).setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        findViewById(R.id.progress_circular).setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(String[] commands) {
        hideProgress();
        start();
    }

    @Override
    public void onCancel(String[] commands) {

    }

    @Override
    public void onFailed(String[] commands, int rc, String out) {

    }
}
