package dk.zbc.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements EggTimerPresenter.View {

    private Button startStopBtn;

    private ImageButton softBoiledBtn;
    private ImageButton mediumBoiledBtn;
    private ImageButton hardBoiledBtn;

    private TextView countDownText;
    private EggTimerPresenter presenter;
    private long timeToCock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startStopBtn = findViewById(R.id.startPauseBtn);
        softBoiledBtn = findViewById(R.id.softBoiledBtn);
        mediumBoiledBtn = findViewById(R.id.mediumBoiledBtn);
        hardBoiledBtn = findViewById(R.id.hardBoiledBtn);
        countDownText = findViewById(R.id.timerTxt);

        startStopBtn.setOnClickListener(startOnClickListener);

        presenter = new EggTimerPresenter(this);
    }

    private View.OnClickListener startOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startTimer();
        }
    };

    private View.OnClickListener stopOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stopTimer();
        }
    };

    public void onButtonEggSelectedClicked(View view) {
        switch (view.getId()) {
            case R.id.softBoiledBtn:
                timeToCock = 300000;
                onCountDown(timeToCock);
                enableStartButton();
                break;
            case R.id.mediumBoiledBtn:
                timeToCock = 420000;
                onCountDown(timeToCock);
                enableStartButton();
                break;
            case R.id.hardBoiledBtn:
                timeToCock = 600000;
                onCountDown(timeToCock);
                enableStartButton();
                break;
            default:
                throw new RuntimeException("Unknown button ID");

        }
    }

    private void enableStartButton() {
        if (!startStopBtn.isEnabled()) {
            startStopBtn.setEnabled(true);
        }
    }

    private void startTimer() {
        startStopBtn.setOnClickListener(stopOnClickListener);
        startStopBtn.setText("Stop");
        disableEggSelectButtons();
        presenter.start(timeToCock);
    }

    private void stopTimer() {
        startStopBtn.setOnClickListener(startOnClickListener);
        startStopBtn.setText("Start");
        enableEggSelectButtons();
        presenter.stop();
    }

    private void disableEggSelectButtons() {
        softBoiledBtn.setEnabled(false);
        mediumBoiledBtn.setEnabled(false);
        hardBoiledBtn.setEnabled(false);
    }

    private void enableEggSelectButtons() {
        softBoiledBtn.setEnabled(true);
        mediumBoiledBtn.setEnabled(true);
        hardBoiledBtn.setEnabled(true);
    }

    private void updateCountDownText(long time) {
        int minutes = (int) (time / 1000) / 60;
        int seconds = (int) (time / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        countDownText.setText(timeLeftFormatted);
    }

    @Override
    public void onCountDown(long timeLeft) {
        updateCountDownText(timeLeft);
    }

    @Override
    public void onEggTimerStopped() {
        enableEggSelectButtons();
    }
}