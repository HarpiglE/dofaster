package com.example.dofaster;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class WhichOneIsLargerFragment extends Fragment {

    private final int LEFT_BUTTON = 0;
    private final int RIGHT_BUTTON = 1;
    private final int EQUAL_BUTTON = 2;

    private TextView whichOneCaution;
    private TextView whichOneTimer;
    private TextView whichOnePoints;
    private Button leftButton;
    private Button rightButton;
    private Button equal;
    private ImageView whichOneEvaluateSign;
    private ImageView timerIcon;
    private ImageView pointsIcon;
    private ConstraintLayout whichOneButtonsContainer;

    private int whichOneBeginningTimerNumberInt = 2;
    private int leftNumber = 0;
    private int rightNumber = 0;
    private int points = 0;

    private boolean gameFinished = false;

    CountDownTimer countDownTimer;

    private String userName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            userName = getArguments().getString("User_Name");
        }

        Log.i("TAG", "passed...");

        return inflater.inflate(R.layout.fragment_which_one_is_larger, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        findViews(view);
        startSpeedMatchBeginningTimer();
    }

    private void findViews(View view) {
        whichOneButtonsContainer = view.findViewById(R.id.which_one_buttons_container);
        timerIcon = view.findViewById(R.id.which_one_timer_icon);
        pointsIcon = view.findViewById(R.id.which_one_points_icon);
        whichOneEvaluateSign = view.findViewById(R.id.which_one_evaluate_sign);
        whichOneCaution = view.findViewById(R.id.which_one_caution);
        whichOneTimer = view.findViewById(R.id.which_one_timer_value);
        whichOnePoints = view.findViewById(R.id.which_one_points_value);
        leftButton = view.findViewById(R.id.which_one_left_button);
        rightButton = view.findViewById(R.id.which_one_right_button);
        equal = view.findViewById(R.id.which_one_bottom_button);
    }

    private void startSpeedMatchBeginningTimer() {

        final int[] numbersShape = {R.drawable.one_sign, R.drawable.two_sign, R.drawable.three_sign};

        countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                whichOneEvaluateSign.setImageResource(numbersShape[whichOneBeginningTimerNumberInt]);
                signAppear();
                whichOneBeginningTimerNumberInt--;
            }

            @Override
            public void onFinish() {
                whichOneButtonsContainer.setVisibility(View.VISIBLE);
                whichOneCaution.setVisibility(View.INVISIBLE);

                startWhichOneMainTimer();
            }
        };
        countDownTimer.start();
    }

    private void signAppear() {
        ObjectAnimator signIncreaseScaleX = ObjectAnimator.ofFloat(
                whichOneEvaluateSign,
                "scaleX",
                0f, 1.5f, 1f
        );
        signIncreaseScaleX.setDuration(250);

        ObjectAnimator signIncreaseScaleY = ObjectAnimator.ofFloat(
                whichOneEvaluateSign,
                "scaleY",
                0f, 1.5f, 1f
        );
        signIncreaseScaleY.setDuration(250);

        AnimatorSet evaluateSignAnimationIncreaseScale = new AnimatorSet();
        evaluateSignAnimationIncreaseScale.playTogether(signIncreaseScaleX, signIncreaseScaleY);
        evaluateSignAnimationIncreaseScale.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                signDisAppear();
            }

        }, 500);
    }

    private void signDisAppear() {
        ObjectAnimator signDecreaseScaleX = ObjectAnimator.ofFloat(
                whichOneEvaluateSign,
                "scaleX",
                1f, 1.5f, 0f
        );
        signDecreaseScaleX.setDuration(250);

        ObjectAnimator signDecreaseScaleY = ObjectAnimator.ofFloat(
                whichOneEvaluateSign,
                "scaleY",
                1f, 1.5f, 0f
        );
        signDecreaseScaleY.setDuration(250);

        AnimatorSet evaluateSignAnimationDecreaseScale = new AnimatorSet();
        evaluateSignAnimationDecreaseScale.playTogether(signDecreaseScaleX, signDecreaseScaleY);
        evaluateSignAnimationDecreaseScale.start();
    }

    private void startWhichOneMainTimer() {
        generateOneLevel();

        countDownTimer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long l) {
                alphaAnimation(whichOneTimer, (int) l / 1000 - 1);
            }

            @Override
            public void onFinish() {

                gameFinished = true;

                if (points != 0) {
                    updateBestScore();
                }

                whichOneCaution.setText(getString(R.string.time_is_up));
                whichOneCaution.setVisibility(View.VISIBLE);

                iconAnimation(timerIcon);
            }
        };
        countDownTimer.start();

        configureButtons();
    }

    protected int generateInt() {
        Random random = new Random();
        int number = random.nextInt();
        number %= 100;
        if (number < 0) {
            number *= -1;
        }
        return number;
    }

    protected void generateOneLevel() {
        leftNumber = generateInt();
        rightNumber = generateInt();
        leftButton.setText(String.valueOf(leftNumber));
        rightButton.setText(String.valueOf(rightNumber));
    }

    protected void evaluate(int whatPressed) {
        switch (whatPressed) {
            case LEFT_BUTTON:
                if (leftNumber > rightNumber) {
                    points++;
                    alphaAnimation(whichOnePoints, points);
                    whichOneEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    whichOneEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;
            case RIGHT_BUTTON:
                if (leftNumber < rightNumber) {
                    points++;
                    alphaAnimation(whichOnePoints, points);
                    whichOneEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    whichOneEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;
            case EQUAL_BUTTON:
                if (leftNumber == rightNumber) {
                    points++;
                    alphaAnimation(whichOnePoints, points);
                    whichOneEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    whichOneEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;
        }
    }

    protected void evaluateAndContinueGame(int whatPressed) {
        if (gameFinished) {
            return;
        }
        evaluate(whatPressed);
        generateOneLevel();
    }

    private void updateBestScore() {
        User user = new User();
        RankList rankList = StoreWhichOneIsLargerScore.getInstance(getContext()).getScoreList();
        user.setUserName(userName);
        user.setUserScore(points);
        rankList.addUser(user);
        StoreWhichOneIsLargerScore.getInstance(getContext()).setScoreList(rankList);
    }

    private void configureButtons() {
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateAndContinueGame(LEFT_BUTTON);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateAndContinueGame(RIGHT_BUTTON);
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateAndContinueGame(EQUAL_BUTTON);
            }
        });
    }

    private void alphaAnimation(TextView text, int value) {
        ObjectAnimator decreaseAlpha = ObjectAnimator.ofFloat(
                text,
                "alpha",
                1f, 0f
        );
        decreaseAlpha.setDuration(500);
        decreaseAlpha.start();

        text.setText(String.valueOf(value));

        ObjectAnimator increaseAlpha = ObjectAnimator.ofFloat(
                text,
                "alpha",
                0f, 1f
        );
        increaseAlpha.setDuration(500);
        increaseAlpha.start();
    }

    private void iconAnimation(ImageView icon) {
        ObjectAnimator shakeThat = ObjectAnimator.ofFloat(
                icon,
                "rotation",
                0f, -45f, 45f, -22.5f, 0
        );
        shakeThat.setDuration(500);

        ObjectAnimator scaleXThat = ObjectAnimator.ofFloat(
                icon,
                "scaleX",
                1f, 1.3f, 1f
        );
        scaleXThat.setDuration(500);

        ObjectAnimator scaleYThat = ObjectAnimator.ofFloat(
                icon,
                "scaleX",
                1f, 1.3f, 1f
        );
        scaleYThat.setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(shakeThat, scaleXThat, scaleYThat);
        animatorSet.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        countDownTimer.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();

        countDownTimer.cancel();
    }
}