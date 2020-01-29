package com.bins.audioeffects;

import com.arthenica.mobileffmpeg.FFmpeg;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FFMpegMediaConverter {

    private static int RETURN_CODE_SUCCESS = 0;
    private static int RETURN_CODE_CANCEL = 1;
    private FFMpegCallback mFFMepcCallback;

    public FFMpegMediaConverter(FFMpegCallback callBack) {
        mFFMepcCallback = callBack;
    }

    /**
     * Function to execute FFMPEG Query
     */
    public void execute(final Options option) {
        this.execute(option.get());
    }

    /**
     * Function to execute FFMPEG Query
     */
    private void execute(final String[] commands) {
        int rc = FFmpeg.execute(commands);

        if (rc == RETURN_CODE_SUCCESS) {
            mFFMepcCallback.onSuccess(commands);
        } else if (rc == RETURN_CODE_CANCEL) {
            mFFMepcCallback.onCancel(commands);
        } else {
            mFFMepcCallback.onFailed(commands, rc, "");
        }
    }

    public void mergeFiles(String voiceFile, String effect, String outFile) {
        File file = new File(outFile);
        if (file.exists()) {
            file.delete();
        }

        String commands = "-i " + voiceFile + " -i " + effect + " -filter_complex amix=inputs=2:duration=first:dropout_transition=2 " + outFile;//working

        String[] cmd1 = commands.split(" ");
        int rc = FFmpeg.execute(cmd1);
        if (rc == RETURN_CODE_SUCCESS) {
            mFFMepcCallback.onSuccess(cmd1);
        } else if (rc == RETURN_CODE_CANCEL) {
            mFFMepcCallback.onCancel(cmd1);
        } else {
            mFFMepcCallback.onFailed(cmd1, rc, "");
        }
    }


    /**
     * @param source1
     * @param effect1
     * @param startOffset in milliseconds
     * @param destination
     */
    public void mergeFiles(String source1, String effect1, int startOffset, String effect2, int startOffset2,String destination) {
        File file = new File(destination);
        if (file.exists()) {
            file.delete();
        }
        String commands = "-i " + source1 + " -i " + effect1 +" -i " + effect2 +
                " -filter_complex [1]adelay=" + startOffset + "|" + startOffset + "[s1];" +
                                 "[2]adelay=" + startOffset2 + "|" + startOffset2 + "[s2];"+
                "[0][s1][s2]amix=3[mixout]" +
                " -map [mixout] -c:v copy " + destination;
        String[] cmd1 = commands.split(" ");
        int rc = FFmpeg.execute(cmd1);
        if (rc == RETURN_CODE_SUCCESS) {
            mFFMepcCallback.onSuccess(cmd1);
        } else if (rc == RETURN_CODE_CANCEL) {
            mFFMepcCallback.onCancel(cmd1);
        } else {
            mFFMepcCallback.onFailed(cmd1, rc, "");
        }
    }


    public void mergeFiles(AudioFile sourceRecording,String destination,AudioFile... effects) {
        File file = new File(destination);
        if (file.exists()) {
            file.delete();
        }
        String commands = "-i " + sourceRecording.getFilePath() +getFileNamesMerged(effects) +
                " -filter_complex "+getDelayParamsMerged(effects) +
                 getMixParamsMerged(effects)+
                " -map [mixout] -c:v copy " + destination;
        String[] cmd1 = commands.split(" ");
        int rc = FFmpeg.execute(cmd1);
        if (rc == RETURN_CODE_SUCCESS) {
            mFFMepcCallback.onSuccess(cmd1);
        } else if (rc == RETURN_CODE_CANCEL) {
            mFFMepcCallback.onCancel(cmd1);
        } else {
            mFFMepcCallback.onFailed(cmd1, rc, "");
        }
    }

    private String getFileNamesMerged(@NotNull  AudioFile... effects){
        StringBuilder stringBuilder = new StringBuilder();
        for(AudioFile file : effects){
            stringBuilder.append(" -i ");
            stringBuilder.append(file.getFilePath());
        }
        return stringBuilder.toString();
    }
    private String getDelayParamsMerged(@NotNull AudioFile... effects){
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for(AudioFile file : effects){
            stringBuilder.append("["+i+"]adelay=");
            stringBuilder.append(file.getStartOffset());
            stringBuilder.append("|");
            stringBuilder.append(file.getStartOffset());
            stringBuilder.append("[s"+i+"];");
            i= i+1;
        }
        return stringBuilder.toString();
    }

    private String getMixParamsMerged(@NotNull AudioFile... effects){
        StringBuilder stringBuilder = new StringBuilder("[0]");
        int i = 1;
        for(AudioFile file : effects){
            stringBuilder.append("[s"+i+"]");
            i= i+1;
        }
        stringBuilder.append("amix="+i+"[mixout]");
        return stringBuilder.toString();
    }

        /**
         * Function to overlay audios
         */
        private void overlayAudios(String fileName1 , String fileName2, String fileName3) {
             String[] cmd = new String[]{
                "-y",
                        "-i",
                        fileName1,
                        "-i",
                        fileName2,
                        "-filter_complex",
                        "amerge=inputs=2",
                        fileName3
            };

            int rc = FFmpeg.execute(cmd);
            if (rc == RETURN_CODE_SUCCESS) {
                mFFMepcCallback.onSuccess(cmd);
            } else if (rc == RETURN_CODE_CANCEL) {
                mFFMepcCallback.onCancel(cmd);
            } else {
                mFFMepcCallback.onFailed(cmd, rc, "");
            }
        }
}
