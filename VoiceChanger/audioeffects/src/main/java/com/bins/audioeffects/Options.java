package com.bins.audioeffects;

final public class Options {

    /**
     * FFMPEG Commands:
     *
     * "-y"  = Mentioning to overwrite output files.
     *
     * "-i"  =  Defining the input file url
     *
     * "-af" = Create the filtergraph specified by filtergraph and use it to filter the stream.
     *
     * "atempo" = The filter accepts exactly one parameter, the audio tempo.
     * If not specified then the filter will assume nominal 1.0 tempo.
     * Tempo must be in the [0.5, 100.0] range.
     *
     * "asetrate" = Set the output sample rate. Default is 44100 Hz.
     *
     * "aecho" =  Apply echoing to the input audio.
     */

    private final String Y ="-y";
    private final String I ="-i";
    private final String AF = "-af";

    //optional params
    private String option1;
    private String option2;
    private String option3;

    //requiredParams
    private String audioAlterOption;
    private String inFilename;
    private String outFilename;


    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getAudioAlterOption() {
        return audioAlterOption;
    }

    public String getInFilename() {
        return inFilename;
    }

    public String getOutFilename() {
        return outFilename;
    }

    public String[] get(){
        return new String []{option1,option2,inFilename,option3,audioAlterOption,outFilename};
    }

    private Options(Builder builder) {
        this.audioAlterOption=builder.audioAlterOption;
        this.inFilename=builder.inFilename;
        this.outFilename=builder.outFilename;
        this.option1= ((builder.option1 == null) ? Y :builder.option1);
        this.option2=((builder.option2 == null) ? I :builder.option1);
        this.option3=((builder.option3 == null) ? AF :builder.option1);
    }

    //Builder Class
    public static class Builder{

        //optional params
        private String option1;
        private String option2;
        private String option3;

        //requiredParams
        private String audioAlterOption;
        private String inFilename;
        private String outFilename;

        public Builder(String audioAlterOption, String inFilename,String outFilename){
            this.audioAlterOption=audioAlterOption;
            this.inFilename=inFilename;
            this.outFilename=outFilename;
        }

        public void setOption1(String option1) {
            this.option1 = option1;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public Options build(){
            return new Options(this);
        }

    }

}
