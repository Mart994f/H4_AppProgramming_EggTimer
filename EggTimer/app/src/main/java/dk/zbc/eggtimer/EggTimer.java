package dk.zbc.eggtimer;

import android.os.CountDownTimer;

import java.util.ArrayList;

public class EggTimer extends Thread {

    private CountDownTimer timer;

    private long remainingCockTime;

    private boolean isStarted;

    private ArrayList<EggTimerListener> listeners;

    public EggTimer(long timeToCock) {
        this.remainingCockTime = timeToCock;
        listeners = new ArrayList<>();
    }

    @Override
    public void run() {
        if (!isStarted) {
            startTimer();
        }
    }

    public void startTimer() {
        timer = new CountDownTimer(remainingCockTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingCockTime = millisUntilFinished;

                for (EggTimerListener l : listeners) {
                    l.onCountDown(remainingCockTime);
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

        isStarted = true;
    }

    public void stopTimer() {
        timer.cancel();
        isStarted = false;
    }

    public void addListener(EggTimerListener listener) {
        listeners.add(listener);
    }


    public void removeListener(EggTimerListener listener) {
        listeners.remove(listener);
    }
}
