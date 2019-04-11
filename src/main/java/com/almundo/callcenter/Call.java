package com.almundo.callcenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Effie on 6/4/2019.
 */
public class Call implements Runnable {

    private final int MAX_DURATION = 10;

    private final int MIN_DURATION = 5;

    private int duration;

    private boolean finished = false;

    private boolean interrupted = false;

    private static final Logger logger = LoggerFactory.getLogger(Call.class);

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    /**
     * Creates an instance of a call.
     *
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

    @Override
    public void run() {
        try {

            logger.info("Call of duration " + duration + " started.");
            TimeUnit.SECONDS.sleep(duration);
            finished = true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("There's been an issue with your call and needs to be interrupted. ");
            interrupted = true;
        }

        logger.info("Call of duration " + duration + " finished.");
    }
}
