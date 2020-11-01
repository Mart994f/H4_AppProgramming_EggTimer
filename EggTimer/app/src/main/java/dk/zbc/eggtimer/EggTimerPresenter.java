package dk.zbc.eggtimer;

public class EggTimerPresenter implements EggTimerListener {

    public interface View{
        void onCountDown(long time);
        void onEggTimerStopped();

    }

    private EggTimer eggTimer;

    private View v;

    public EggTimerPresenter(View v) {
        this.v = v;
    }

    public void stop() {
        eggTimer.removeListener(this);
        eggTimer.stopTimer();
    }

    public void start(long timeToCook) {
        eggTimer = new EggTimer(timeToCook);
        eggTimer.addListener(this);
        eggTimer.run();
    }

    @Override
    public void onCountDown(long timeLeft) {
        v.onCountDown(timeLeft);
    }

    @Override
    public void onEggTimerStopped() {
        eggTimer.removeListener(this);
        v.onEggTimerStopped();
    }
}
