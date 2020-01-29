package com.bins.audioeffects;

public class AudioFile {
   private String filePath;
   private int startOffset;

   public AudioFile(String filePath,int startOffset){
       this.filePath = filePath;
       this.startOffset = startOffset;
   }

    public String getFilePath() {
        return filePath;
    }


    public int getStartOffset() {
        return startOffset;
    }

}
