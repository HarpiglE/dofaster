package com.example.dofaster;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class SpeedMatchFragment extends Fragment {

    private LinearLayout speedMatchButtonsContainer;
    private Button bothButton;
    private Button oneOfThemButton;
    private Button noOneButton;
    private ImageView speedMatchPic;
    private ImageView speedMatchEvaluateSign;
    private ImageView timerIcon;
    private ImageView pointsIcon;
    private TextView speedMatchPoints;
    private TextView speedMatchTimer;
    private TextView caution;

    private int speedMatchBeginningTimerNumberInt = 2;
    private int points = 0;

    private boolean gameFinished = false;

    private String userName;
    private String whichShapePrevious;
    private String whichColorPrevious;
    private String whichShapeNow = null;
    private String whichColorNow = null;

    private String[] colors = {"#ffe4ab00", "#ffe40004", "#ff217306", "#ff00a3e4"};

    private CountDownTimer countDownTimer;

    public SpeedMatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            userName = getArguments().getString("User_Name");
        }
        return inflater.inflate(R.layout.fragment_speed_match, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);
        startSpeedMatchBeginningTimer();
    }

    private void findViews(@NonNull View view) {
        speedMatchButtonsContainer = view.findViewById(R.id.speed_match_buttons_container);
        bothButton = view.findViewById(R.id.both_btn);
        oneOfThemButton = view.findViewById(R.id.one_of_them_btn);
        noOneButton = view.findViewById(R.id.no_one_btn);
        speedMatchPic = view.findViewById(R.id.speed_match_pic);
        speedMatchEvaluateSign = view.findViewById(R.id.speed_match_evaluate_sign);
        timerIcon = view.findViewById(R.id.speed_match_timer_icon);
        pointsIcon = view.findViewById(R.id.speed_match_points_icon);
        speedMatchPoints = view.findViewById(R.id.speed_match_points_value);
        speedMatchTimer = view.findViewById(R.id.speed_match_timer_value);
        caution = view.findViewById(R.id.speed_match_caution);
    }

    private void startSpeedMatchBeginningTimer() {
        changeShapeAndColor();

        final int[] numbersShape = {R.drawable.one_sign, R.drawable.two_sign, R.drawable.three_sign};

        countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                speedMatchEvaluateSign.setImageResource(numbersShape[speedMatchBeginningTimerNumberInt]);
                signAppear();
                speedMatchBeginningTimerNumberInt--;
            }

            @Override
            public void onFinish() {
                speedMatchButtonsContainer.setVisibility(View.VISIBLE);
                caution.setVisibility(View.GONE);

                startSpeedMatchMainTimer();
                changeShapeAndColor();
            }
        };

        countDownTimer.start();
    }

    private void changeShapeAndColor() {
        whichShapePrevious = whichShapeNow;
        whichColorPrevious = whichColorNow;

        switch (generateRandomShape()) {
            case 1:
                speedMatchPic.setImageResource(R.drawable.circle);
                whichColorNow = generateRandomColor();
                speedMatchPic.setColorFilter(Color.parseColor(whichColorNow));
                whichShapeNow = "circle";
                break;

            case 2:
                speedMatchPic.setImageResource(R.drawable.diamond);
                whichColorNow = generateRandomColor();
                speedMatchPic.setColorFilter(Color.parseColor(whichColorNow));
                whichShapeNow = "diamond";
                break;

            case 3:
                speedMatchPic.setImageResource(R.drawable.square);
                whichColorNow = generateRandomColor();
                speedMatchPic.setColorFilter(Color.parseColor(whichColorNow));
                whichShapeNow = "square";
                break;
        }
    }

    private void signAppear() {
        ObjectAnimator signIncreaseScaleX = ObjectAnimator.ofFloat(
                speedMatchEvaluateSign,
                "scaleX",
                0f, 1.5f, 1f
        );
        signIncreaseScaleX.setDuration(250);

        ObjectAnimator signIncreaseScaleY = ObjectAnimator.ofFloat(
                speedMatchEvaluateSign,
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
                speedMatchEvaluateSign,
                "scaleX",
                1f, 1.5f, 0f
        );
        signDecreaseScaleX.setDuration(250);

        ObjectAnimator signDecreaseScaleY = ObjectAnimator.ofFloat(
                speedMatchEvaluateSign,
                "scaleY",
                1f, 1.5f, 0f
        );
        signDecreaseScaleY.setDuration(250);

        AnimatorSet evaluateSignAnimationDecreaseScale = new AnimatorSet();
        evaluateSignAnimationDecreaseScale.playTogether(signDecreaseScaleX, signDecreaseScaleY);
        evaluateSignAnimationDecreaseScale.start();
    }

    private void startSpeedMatchMainTimer() {
        countDownTimer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long l) {
                alphaAnimation(speedMatchTimer, (int) l / 1000 - 1);
            }

            @Override
            public void onFinish() {
                gameFinished = true;

                speedMatchButtonsContainer.setVisibility(View.INVISIBLE);
                caution.setText(getString(R.string.time_is_up));
                caution.setVisibility(View.VISIBLE);

                iconAnimation(timerIcon);

                if (points != 0) {
                    updateBestScore();
                }
            }
        };
        countDownTimer.start();

        configureButtons();
    }

    private void configureButtons() {
        bothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate(0);
                changeShapeAndColor();
            }
        });

        oneOfThemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate(1);
                changeShapeAndColor();
            }
        });

        noOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate(2);
                changeShapeAndColor();
            }
        });
    }

    private void evaluate(int button) {
        if (gameFinished) {
            return;
        }

        switch (button) {
            case 0:
                if (whichShapePrevious.equals(whichShapeNow) && whichColorPrevious.equals(whichColorNow)) {
                    points++;
                    alphaAnimation(speedMatchPoints, points);
                    speedMatchEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    speedMatchEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;

            case 1:
                if (whichShapePrevious.equals(whichShapeNow) ^ whichColorPrevious.equals(whichColorNow)) {
                    points++;
                    alphaAnimation(speedMatchPoints, points);
                    speedMatchEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    speedMatchEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;

            case 2:
                if (!whichShapePrevious.equals(whichShapeNow) && !whichColorPrevious.equals(whichColorNow)) {
                    points++;
                    alphaAnimation(speedMatchPoints, points);
                    speedMatchEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    speedMatchEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;
        }
    }

    private int generateRandomShape() {
        Random random = new Random();
        return random.nextInt(3) + 1;
    }

    private String generateRandomColor() {
        Random random = new Random();
        return colors[random.nextInt(4)];
    }

    private void updateBestScore() {
        User user = new User();
        RankList rankList = StoreSpeedMatchScore.getInstance(getContext()).getScoreList();
        user.setUserName(userName);
        user.setUserScore(points);
        rankList.addUser(user);
        StoreSpeedMatchScore.getInstance(getContext()).setScoreList(rankList);
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