package com.tilldawn.Control;

public class TimerController {
    private static TimerController instance;
    private float countdownTime;
    private float initialTime;
    private boolean paused = false; // ✅ اضافه کردن وضعیت توقف

    private TimerController(float totalTime) {
        this.countdownTime = totalTime;
        this.initialTime = totalTime;
    }

    public static void init(float totalTime) {
        if (instance == null) {
            instance = new TimerController(totalTime);
        }
    }

    public static TimerController getInstance() {
        return instance;
    }

    public void update(float delta) {
        // فقط وقتی که pause نیست زمان کم بشه ✅
        if (!paused && countdownTime > 0) {
            countdownTime -= delta;
            if (countdownTime < 0) countdownTime = 0;
        }
    }
//متد زمان گذشته شده
    public float getElapsedTime() {
        return initialTime - countdownTime;
    }

    public float getTimeRemaining() {
        return countdownTime;
    }

    public boolean isFinished() {
        return countdownTime <= 0;
    }

    // ✅ متد برای pause و resume
    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }


    //استفاده از پاز و ...
//    TimerController.getInstance().resume();
//TimerController.getInstance().pause();
}
