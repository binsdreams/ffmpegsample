package com.bins.audioeffects;

public class Effects {
    
    public static String RADIO = "atempo=1";
    public static String CHIPMUNCK = "asetrate=22100,atempo=1/2";
    public static String ROBOT = "asetrate=11100,atempo=4/3,atempo=1/2,atempo=3/4";
    public static String CAVE = "aecho=0.8:0.9:1000:0.3";

    /**
     * Adjust audio tempo.
     *
     * Min  - 0.5
     * Max - 2
     * Default - 1
     * Unit  -  BPM (beats per minute)
     *
     * Description
     *
     * The filter accepts exactly one parameter, the audio tempo. If not specified then the filter will assume nominal 1.0 tempo.
     *
     * Note that tempo greater than 2 will skip some samples rather than blend them in.
     * If for any reason this is a concern it is always possible to daisy-chain several instances of atempo to achieve the desired product tempo.
     *
     * Example of the usage
     *
     * Slow down audio to 80% tempo: set the syntax "atempo=0.8"
     * To speed up audio to 200% tempo: syntax : "atempo=2"
     *
     * To speed up audio to 300% tempo by daisy-chaining two atempo instances: "atempo=sqrt(3),atempo=sqrt(3)"
     *
     */

    /**
     * asetrate
     *
     * Min  - 1 Hz.
     * Max - MAX_INT (2,147,483,647)  Hz.
     * Default - 44100  Hz.
     * Unit  = HZ
     *
     * Description
     *
     * Set the sample rate without altering the PCM data. This will result in a change of speed and pitch.
     *
     * Example of the usage
     *
     * The filter accepts the following options:
     *
     * Syntax : sample_rate, r
     * Set the output sample rate : "asetrate=22100"
     *
     */

    /**
     * aecho
     * Apply echoing to the input audio.
     *
     * A description of the accepted parameters follows.
     *
     * in_gain  : Set input gain of reflected signal.
     *
     * Min  - 0
     * Max - 1
     * Default - 0.6
     * Unit - percentage in gain for reflected signal
     *
     * out_gain : Set output gain of reflected signal.
     *
     * Min  - 0
     * Max - 1
     * Default - 0.3
     * Unit - percentage out gain for reflected signal
     *
     * delays : Set list of time intervals in milliseconds between original signal and reflections separated by ’|’.
     *
     * Min  - 0
     * Max - 90000
     * Default - 1000
     * Unit - Milliseconds
     *
     * decays  : Set list of loudness of reflected signals separated by ’|’. Allowed range for each decay
     *
     * Min  - 0
     * Max - 1
     * Default - 05
     * Unit  = Echo decay per delay.
     *
     *  Description
     *
     * Echoes are reflected sound and can occur naturally
     * amongst mountains (and sometimes large buildings) when talking or shouting;
     * digital echo effects emulate this behaviour and are often used to help fill out the sound of a single instrument or vocal.
     * The time difference between the original signal and the reflection is the delay, and the loudness of the reflected signal is the decay.
     * Multiple echoes can have different delays and decays.
     *
     * Example of the usage **** Keep the order of the parameters ***
     *
     * Make it sound as if there are twice as many instruments as are actually playing: "aecho=0.8:0.88:60:0.4"
     * If delay is very short, then it sounds like a (metallic) robot playing music: "aecho=0.8:0.88:6:0.4"
     * A longer delay will sound like an open air concert in the mountains: "aecho=0.8:0.9:1000:0.3"
     * Same as above but with one more mountain: "aecho=0.8:0.9:1000|1800:0.3|0.25"
     */

    /**
     * Add a chorus effect to the audio.
     *
     * Can make a single vocal sound like a chorus, but can also be applied to instrumentation.
     *
     * in_gain  : Set input gain of reflected signal.
     *
     * Min  - 0
     * Max - 1
     * Default - 0.4
     * Unit - percentage in gain for reflected signal
     *
     * out_gain : Set output gain of reflected signal.
     *
     * Min  - 0
     * Max - 1
     * Default - 0.4
     * Unit - percentage out gain for reflected signal
     *
     * delays : Set list of time intervals in milliseconds between original signal and reflections separated by ’|’.
     *
     * Min  - 0
     * Max - 60ms
     * Default - 40m
     * Unit - Milliseconds
     *
     * decays  : Set list of loudness of reflected signals separated by ’|’. Allowed range for each decay
     *
     * Min  - 0
     * Max - 1
     * Default - 05
     * Unit  = Echo decay per delay.
     *
     * speeds
     *
     * Min  : -16
     * Max : 16
     * Default : 1
     * Unit : +ve will increase speed, -ver will reduce speed
     *
     *  depths
     *
     *  Min  : 8
     *  Max : 16
     *  Default : 8
     *
     *  Description
     *
     * Chorus resembles an echo effect with a short delay, but whereas with echo the delay is constant,
     * with chorus, it is varied using using sinusoidal or triangular modulation.
     * The modulation depth defines the range the modulated delay is played before or after the delay.
     * Hence the delayed sound will sound slower or faster, that is the delayed sound tuned around the original one,
     * like in a chorus where some vocals are slightly off key.
     *
     *  Example of the usage **** Keep the order of the parameters ***
     *
     * A single delay: "chorus=0.7:0.9:55:0.4:0.25:2"
     * Two delays: : "chorus=0.6:0.9:50|60:0.4|0.32:0.25|0.4:2|1.3"
     * Fuller sounding chorus with three delays:  chorus=0.5:0.9:50|60|40:0.4|0.32|0.3:0.25|0.4|0.3:2|2.3|1.3
     */

    /**
     * https://www.ffmpeg.org/ffmpeg-filters.html
     */
}
