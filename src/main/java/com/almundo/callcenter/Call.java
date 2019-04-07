package com.almundo.callcenter;

/**
 * Created by Effie on 6/4/2019.
 */
public class Call {

    private final int MAX_DURATION = 10;

    private final int MIN_DURATION = 5;

    private int duration;

    /**
     * Creates an instance of a call.
     * @param duration The measure is seconds.
     */
    public Call(int duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
