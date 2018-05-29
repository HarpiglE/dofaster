package com.example.dofaster.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
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

public class CMFragment extends Fragment {

    private final int NO_BTN = 0;
    private final int YES_BTN = 1;

    private Button yesBtn;
    private Button noBtn;
    private Button upCard;
    private Button downCard;
    private ImageView CMEvaluateSign;
    private ImageView timerIcon;
    private ImageView pointsIcon;
    private TextView CMCaution;
    private TextView CMTimer;
    private TextView CMPoints;

    private int points = 0;

    private int CMBeginningTimerNumberInt = 2;
    private int colorNameIndex;
    private int colorCodeIndex;
    private int[] colorsCode = {
            Color.parseColor("#ff000000"),
            Color.parseColor("#ff00a3e4"),
            Color.parseColor("#ff217306"),
            Color.parseColor("#ffe40004")
    };

    private String username;
    private String[] colorsName = {"مشکی", "آبی", "سبز", "قرمز"};

    private CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        if (getArguments() != null) {
            username = getArguments().getString("username");
        }
        return inflater.inflate(R.layout.fragment_cm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);
        startCMBeginningTimer();
    }

    private void findViews(View view) {
        timerIcon = view.findViewById(R.id.CM_time_icon);
        pointsIcon = view.findViewById(R.id.CM_points_icon);
        CMEvaluateSign = view.findViewById(R.id.CM_evaluate_sign);
        CMCaution = view.findViewById(R.id.CM_caution);
        CMTimer = view.findViewById(R.id.CM_time_value);
        CMPoints = view.findViewById(R.id.CM_points_value);
        upCard = view.findViewById(R.id.CM_up_card_view);
        downCard = view.findViewById(R.id.CM_down_card_view);
        yesBtn = view.findViewById(R.id.CM_yes_btn);
        noBtn = view.findViewById(R.id.CM_no_btn);
    }

    private void startCMBeginningTimer() {

        final int[] numbersShape = {
                R.drawable.one_sign, R.drawable.two_sign, R.drawable.three_sign
        };

        countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                CMEvaluateSign.setImageResource(numbersShape[CMBeginningTimerNumberInt]);
                signAppear();
                CMBeginningTimerNumberInt--;
            }

            @Override
            public void onFinish() {
                upCard.setEnabled(true);
                downCard.setEnabled(true);
                yesBtn.setEnabled(true);
                noBtn.setEnabled(true);

                startCMMainTimer();
            }
        };
        countDownTimer.start();
    }

    private void signAppear() {
        ObjectAnimator signIncreaseScaleX = ObjectAnimator.ofFloat(
                CMEvaluateSign,
                "scaleX",
                0f, 1.5f, 1f
        );
        signIncreaseScaleX.setDuration(250);

        ObjectAnimator signIncreaseScaleY = ObjectAnimator.ofFloat(
                CMEvaluateSign,
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
                CMEvaluateSign,
                "scaleX",
                1f, 1.5f, 0f
        );
        signDecreaseScaleX.setDuration(250);

        ObjectAnimator signDecreaseScaleY = ObjectAnimator.ofFloat(
                CMEvaluateSign,
                "scaleY",
                1f, 1.5f, 0f
        );
        signDecreaseScaleY.setDuration(250);

        AnimatorSet evaluateSignAnimationDecreaseScale = new AnimatorSet();
        evaluateSignAnimationDecreaseScale.playTogether(signDecreaseScaleX, signDecreaseScaleY);
        evaluateSignAnimationDecreaseScale.start();
    }

    private void startCMMainTimer() {
        generateOneLevel();

        countDownTimer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long l) {
                alphaAnimation(CMTimer, (int) l / 1000 - 1);
            }

            @Override
            public void onFinish() {
                upCard.setEnabled(false);
                downCard.setEnabled(false);
                yesBtn.setEnabled(false);
                noBtn.setEnabled(false);

                if (points != 0) {
                    updateBestScore();
                }



                CMCaution.setText(getString(R.string.time_is_up));
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

    private void generateOneLevel() {
        colorNameIndex = generateInt(4, 0);
        colorCodeIndex = generateInt(4, 0);

        upCard.setText(colorsName[colorNameIndex]);
        downCard.setText(colorsName[generateInt(4, 0)]);
        downCard.setTextColor(colorsCode[colorCodeIndex]);

    }

    private void evaluate(int whatPressed) {
        switch (whatPressed) {
            case NO_BTN:
                if (colorNameIndex != colorCodeIndex) {
                    points++;
                    alphaAnimation(CMPoints, points);
                    CMEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    CMEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;

            case YES_BTN:
                if (colorNameIndex == colorCodeIndex) {
                    points++;
                    alphaAnimation(CMPoints, points);
                    CMEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    CMEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;
        }
    }

    protected void evaluateAndContinueGame(int whatPressed) {
        evaluate(whatPressed);
        generateOneLevel();
    }

    private void updateBestScore() {
        User user = new User();
        StoreGamesRank.changeSharedPreferencesName("color_match");
        RankList rankList = StoreGamesRank.getInstance(getContext()).getScoreList();
        user.setUserName(username);
        user.setUserScore(points);
        rankList.addUser(user);
        StoreGamesRank.getInstance(getContext()).setScoreList(rankList);
    }

    private void configureButtons() {
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateAndContinueGame(NO_BTN);
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateAndContinueGame(YES_BTN);
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
}
