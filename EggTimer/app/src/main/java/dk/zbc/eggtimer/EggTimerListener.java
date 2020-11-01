package dk.zbc.eggtimer;

public interface EggTimerListener {

    void onCountDown(long timeLeft);

    void onEggTimerStopped();

}
