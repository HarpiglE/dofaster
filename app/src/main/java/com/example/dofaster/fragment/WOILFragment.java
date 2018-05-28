package com.example.dofaster.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dofaster.R;
import com.example.dofaster.data.RankList;
import com.example.dofaster.data.StoreGamesRank;
import com.example.dofaster.data.User;

import java.util.Random;

public class WOILFragment extends Fragment {

    private final int UP_CARD = 0;
    private final int DOWN_CARD = 1;
    private final int EQUAL_BTN = 2;

    private Button equal;
    private Button upCard;
    private Button downCard;
    private ImageView WOILEvaluateSign;
    private ImageView timerIcon;
    private ImageView pointsIcon;
    private TextView WOILCaution;
    private TextView WOILTimer;
    private TextView WOILPoints;

    private int levelDifficultyDeadline;
    private int levelDifficultyCounter = 0;
    private int WOILBeginningTimerNumberInt = 2;
    private int upNumber = 0;
    private int downNumber = 0;
    private int points = 0;

    private boolean gameFinished = false;

    private CountDownTimer countDownTimer;

    private String userName;
    private String[] operationList = {"+", "-", "\u00F7", "\u00D7"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState
    ) {
        if (getArguments() != null) {
            userName = getArguments().getString("User_Name");
        }
        return inflater.inflate(R.layout.fragment_woil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);
        startWOILBeginningTimer();
    }

    private void findViews(View view) {
        timerIcon = view.findViewById(R.id.WOIL_time_icon);
        pointsIcon = view.findViewById(R.id.WOIL_points_icon);
        WOILEvaluateSign = view.findViewById(R.id.WOIL_evaluate_sign);
        WOILCaution = view.findViewById(R.id.WOIL_caution);
        WOILTimer = view.findViewById(R.id.WOIL_time_value);
        WOILPoints = view.findViewById(R.id.WOIL_points_value);
        upCard = view.findViewById(R.id.WOIL_up_card_view);
        downCard = view.findViewById(R.id.WOIL_down_card_view);
        equal = view.findViewById(R.id.WOIL_equal_btn);
    }

    private void startWOILBeginningTimer() {

        final int[] numbersShape = {
                R.drawable.one_sign, R.drawable.two_sign, R.drawable.three_sign
        };

        countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                WOILEvaluateSign.setImageResource(numbersShape[WOILBeginningTimerNumberInt]);
                signAppear();
                WOILBeginningTimerNumberInt--;
            }

            @Override
            public void onFinish() {
                upCard.setEnabled(true);
                downCard.setEnabled(true);
                equal.setEnabled(true);

                startWOILMainTimer();
            }
        };
        countDownTimer.start();
    }

    private void signAppear() {
        ObjectAnimator signIncreaseScaleX = ObjectAnimator.ofFloat(
                WOILEvaluateSign,
                "scaleX",
                0f, 1.5f, 1f
        );
        signIncreaseScaleX.setDuration(250);

        ObjectAnimator signIncreaseScaleY = ObjectAnimator.ofFloat(
                WOILEvaluateSign,
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
                WOILEvaluateSign,
                "scaleX",
                1f, 1.5f, 0f
        );
        signDecreaseScaleX.setDuration(250);

        ObjectAnimator signDecreaseScaleY = ObjectAnimator.ofFloat(
                WOILEvaluateSign,
                "scaleY",
                1f, 1.5f, 0f
        );
        signDecreaseScaleY.setDuration(250);

        AnimatorSet evaluateSignAnimationDecreaseScale = new AnimatorSet();
        evaluateSignAnimationDecreaseScale.playTogether(signDecreaseScaleX, signDecreaseScaleY);
        evaluateSignAnimationDecreaseScale.start();
    }

    private void startWOILMainTimer() {
        levelDifficultyDeadline = generateInt(3, 4);

        generateOneLevel();

        countDownTimer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long l) {
                alphaAnimation(WOILTimer, (int) l / 1000 - 1);
            }

            @Override
            public void onFinish() {
                upCard.setEnabled(false);
                downCard.setEnabled(false);
                equal.setEnabled(false);

                gameFinished = true;

                if (points != 0) {
                    updateBestScore();
                }

                WOILCaution.setText(getString(R.string.time_is_up));
                WOILCaution.setVisibility(View.VISIBLE);

                iconAnimation(timerIcon);
            }
        };
        countDownTimer.start();

        configureButtons();
    }

    protected int generateInt(int base, int add) {
        Random random = new Random();
        return random.nextInt(base) + add;
    }

    protected void generateOneLevel() {
        if (levelDifficultyCounter < levelDifficultyDeadline) {
            upNumber = generateInt(100, 0);
            downNumber = generateInt(100, 0);

            upCard.setText(String.valueOf(upNumber));
            downCard.setText(String.valueOf(downNumber));

            levelDifficultyCounter++;
        } else {
            // Set UP card numbers for evaluate and expression strings for show
            if (generateInt(10, 1) <= 6) {
                upNumber = generateExpression(upCard, 2);
            } else if (generateInt(10, 1) == 10) {
                upNumber = generateExpression(upCard, 1);
            } else {
                upNumber = generateExpression(upCard, 3);
            }

            // Set DOWN card numbers for evaluate and expression strings for show
            if (generateInt(10, 1) <= 6) {
                downNumber = generateExpression(downCard, 2);
            } else if (generateInt(10, 1) == 10) {
                downNumber = generateExpression(downCard, 1);
            } else {
                downNumber = generateExpression(downCard, 3);
            }
        }
    }

    protected void evaluate(int whatPressed) {
        switch (whatPressed) {
            case UP_CARD:
                if (upNumber > downNumber) {
                    points++;
                    alphaAnimation(WOILPoints, points);
                    WOILEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    WOILEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;
            case DOWN_CARD:
                if (upNumber < downNumber) {
                    points++;
                    alphaAnimation(WOILPoints, points);
                    WOILEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    WOILEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;
            case EQUAL_BTN:
                if (upNumber == downNumber) {
                    points++;
                    alphaAnimation(WOILPoints, points);
                    WOILEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    WOILEvaluateSign.setImageResource(R.drawable.wrong_sign);
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
        RankList rankList = StoreGamesRank
                .getInstance(getContext(), "WOIL")
                .getScoreList();
        user.setUserName(userName);
        user.setUserScore(points);
        rankList.addUser(user);
        StoreGamesRank.getInstance(getContext(), "WOIL").setScoreList(rankList);
    }

    private void configureButtons() {
        upCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateAndContinueGame(UP_CARD);
            }
        });

        downCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateAndContinueGame(DOWN_CARD);
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateAndContinueGame(EQUAL_BTN);
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

    private int generateExpression(Button card, int difficulty) {
        String expression = "";

        int number = 0;
        int firstNumber = generateInt(30, 1);
        int secondNumber = generateInt(30, 1);
        int thirdNumber = generateInt(20, 1);

        if (difficulty == 1) {

            number = generateInt(100, 0);
            card.setText(String.valueOf(number));
            return number;

        } else if (difficulty == 2) {

            switch (operationList[generateInt(4, 0)]) {
                case "+":
                    expression = String.valueOf(firstNumber + " + " + secondNumber);
                    card.setText(expression);
                    number = firstNumber + secondNumber;
                    break;
                case "-":
                    expression = String.valueOf(firstNumber + " - " + secondNumber);
                    card.setText(expression);
                    number = firstNumber - secondNumber;
                    break;
                case "\u00F7":
                    expression = String.valueOf(firstNumber + " \u00F7 " + secondNumber);
                    card.setText(expression);
                    number = firstNumber / secondNumber;
                    break;
                case "\u00D7":
                    expression = String.valueOf(firstNumber + " \u00D7 " + secondNumber);
                    card.setText(expression);
                    number = firstNumber * secondNumber;
                    break;
            }

            return number;

        } else {

            switch (operationList[generateInt(4, 0)]) {
                case "+":
                    expression = String.valueOf(
                            "(" + firstNumber + " + " + secondNumber + ")"
                    );
                    number = firstNumber + secondNumber;
                    break;
                case "-":
                    expression = String.valueOf(
                            "(" + firstNumber + " + " + secondNumber + ")"
                    );
                    number = firstNumber - secondNumber;
                    break;
                case "\u00F7":
                    expression = String.valueOf(
                            "(" + firstNumber + " + " + secondNumber + ")"
                    );
                    number = firstNumber / secondNumber;
                    break;
                case "\u00D7":
                    expression = String.valueOf(
                            "(" + firstNumber + " + " + secondNumber + ")"
                    );
                    number = firstNumber * secondNumber;
                    break;
            }

            switch (operationList[generateInt(4, 0)]) {
                case "+":
                    expression = expression + String.valueOf(" + " + thirdNumber);
                    card.setText(expression);
                    number = number + thirdNumber;
                    break;
                case "-":
                    expression = expression + String.valueOf(" - " + thirdNumber);
                    card.setText(expression);
                    number = number - thirdNumber;
                    break;
                case "\u00F7":
                    expression = expression + String.valueOf(" \u00F7 " + thirdNumber);
                    card.setText(expression);
                    number = number / thirdNumber;
                    break;
                case "\u00D7":
                    expression = expression + String.valueOf(" \u00D7 " + thirdNumber);
                    card.setText(expression);
                    number = number * thirdNumber;
                    break;
            }

            return number;
        }
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