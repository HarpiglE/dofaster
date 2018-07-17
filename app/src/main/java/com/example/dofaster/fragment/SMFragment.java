package com.example.dofaster.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dofaster.R;
import com.example.dofaster.model.RankList;
import com.example.dofaster.model.StoreGamesRank;
import com.example.dofaster.model.User;

import java.util.Random;

public class SMFragment extends Fragment {

    private Button bothBtn;
    private Button oneBtn;
    private Button noBtn;
    private CardView picContainer;
    private ImageView SMPic;
    private ImageView SMEvaluateSign;
    private ImageView timerIcon;
    private ImageView pointsIcon;
    private ImageView bestScoreSign;
    private TextView SMPoints;
    private TextView SMTimer;
    private TextView SMCaution;
    private TextView scorePopup;
    private View scorePopupBg;

    private int SMBeginningTimerNumberInt = 2;
    private int points = 0;

    private boolean isBestScore = false;

    private String username;
    private String whichShapePrevious;
    private String whichColorPrevious;
    private String whichShapeNow = null;
    private String whichColorNow = null;

    private String[] colors = {"#ffe4ab00", "#ffe40004", "#ff217306", "#ff00a3e4"};

    private CountDownTimer countDownTimer;

    public SMFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            username = getArguments().getString("username");
        }
        return inflater.inflate(R.layout.fragment_sm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);
        startSMBeginningTimer();
    }

    private void findViews(@NonNull View view) {
        bothBtn = view.findViewById(R.id.both_btn);
        oneBtn = view.findViewById(R.id.one_of_them_btn);
        noBtn = view.findViewById(R.id.no_one_btn);
        SMPic = view.findViewById(R.id.SM_pic);
        SMEvaluateSign = view.findViewById(R.id.SM_evaluate_sign);
        timerIcon = view.findViewById(R.id.SM_time_icon);
        pointsIcon = view.findViewById(R.id.SM_points_icon);
        SMPoints = view.findViewById(R.id.SM_points_value);
        SMTimer = view.findViewById(R.id.SM_time_value);
        SMCaution = view.findViewById(R.id.SM_caution);
        scorePopup = view.findViewById(R.id.SM_score_popup);
        scorePopupBg = view.findViewById(R.id.SM_score_popup_bg);
        bestScoreSign = view.findViewById(R.id.SM_best_sign);
        picContainer = view.findViewById(R.id.SM_pic_container);
    }

    private void startSMBeginningTimer() {
        changeShapeAndColor();

        final int[] numbersShape = {
                R.drawable.one_sign, R.drawable.two_sign, R.drawable.three_sign
        };

        countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                SMEvaluateSign.setImageResource(numbersShape[SMBeginningTimerNumberInt]);
                signAppear();
                SMBeginningTimerNumberInt--;
            }

            @Override
            public void onFinish() {
                bothBtn.setEnabled(true);
                oneBtn.setEnabled(true);
                noBtn.setEnabled(true);

                startSMMainTimer();
                cardAnimation();
            }
        };

        countDownTimer.start();
    }

    private void changeShapeAndColor() {
        whichShapePrevious = whichShapeNow;
        whichColorPrevious = whichColorNow;

        switch (generateRandomShape()) {
            case 1:
                SMPic.setImageResource(R.drawable.circle);
                whichColorNow = generateRandomColor();
                SMPic.setColorFilter(Color.parseColor(whichColorNow));
                whichShapeNow = "circle";
                break;

            case 2:
                SMPic.setImageResource(R.drawable.diamond);
                whichColorNow = generateRandomColor();
                SMPic.setColorFilter(Color.parseColor(whichColorNow));
                whichShapeNow = "diamond";
                break;

            case 3:
                SMPic.setImageResource(R.drawable.square);
                whichColorNow = generateRandomColor();
                SMPic.setColorFilter(Color.parseColor(whichColorNow));
                whichShapeNow = "square";
                break;
        }
    }

    private void signAppear() {
        ObjectAnimator signIncreaseScaleX = ObjectAnimator.ofFloat(
                SMEvaluateSign,
                "scaleX",
                0f, 1.5f, 1f
        );
        signIncreaseScaleX.setDuration(250);

        ObjectAnimator signIncreaseScaleY = ObjectAnimator.ofFloat(
                SMEvaluateSign,
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
                SMEvaluateSign,
                "scaleX",
                1f, 1.5f, 0f
        );
        signDecreaseScaleX.setDuration(250);

        ObjectAnimator signDecreaseScaleY = ObjectAnimator.ofFloat(
                SMEvaluateSign,
                "scaleY",
                1f, 1.5f, 0f
        );
        signDecreaseScaleY.setDuration(250);

        AnimatorSet evaluateSignAnimationDecreaseScale = new AnimatorSet();
        evaluateSignAnimationDecreaseScale.playTogether(signDecreaseScaleX, signDecreaseScaleY);
        evaluateSignAnimationDecreaseScale.start();
    }

    private void startSMMainTimer() {
        countDownTimer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long l) {
                alphaAnimation(SMTimer, (int) l / 1000 - 1);
            }

            @Override
            public void onFinish() {
                bothBtn.setEnabled(false);
                oneBtn.setEnabled(false);
                noBtn.setEnabled(false);

                if (points != 0) {
                    updateBestScore();
                    scorePopup.setText(getString(R.string.score_popup, points));
                } else {
                    scorePopup.setText(getString(R.string.no_score_popup));
                }

                SMCaution.setText(getString(R.string.time_is_up));
                iconAnimation(timerIcon);
                alphaAnimation(scorePopupBg);
                popupAppearAnimation();
                transactionToRankListFragment();
            }
        };
        countDownTimer.start();

        configureButtons();
    }

    private void configureButtons() {
        bothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate(0);
                cardAnimation();
            }
        });

        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate(1);
                cardAnimation();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate(2);
                cardAnimation();
            }
        });
    }

    private void evaluate(int button) {
        switch (button) {
            case 0:
                if (whichShapePrevious
                        .equals(whichShapeNow) && whichColorPrevious.equals(whichColorNow)) {
                    points++;
                    alphaAnimation(SMPoints, points);
                    SMEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    SMEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;

            case 1:
                if (whichShapePrevious
                        .equals(whichShapeNow) ^ whichColorPrevious.equals(whichColorNow)) {
                    points++;
                    alphaAnimation(SMPoints, points);
                    SMEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    SMEvaluateSign.setImageResource(R.drawable.wrong_sign);
                    signAppear();
                }
                break;

            case 2:
                if (!whichShapePrevious
                        .equals(whichShapeNow) && !whichColorPrevious.equals(whichColorNow)) {
                    points++;
                    alphaAnimation(SMPoints, points);
                    SMEvaluateSign.setImageResource(R.drawable.correct_sign);
                    signAppear();
                    iconAnimation(pointsIcon);
                } else {
                    SMEvaluateSign.setImageResource(R.drawable.wrong_sign);
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
        StoreGamesRank.changeSharedPreferencesName("speed_match");
        RankList rankList = StoreGamesRank.getInstance(getContext()).getScoreList();

        try {
            if (points > rankList.getRankList().get(0).getUserScore()) {
                isBestScore = true;
            }
        } catch (IndexOutOfBoundsException e) {
            // Meaning the rank list has no element
            isBestScore = true;
        }

        user.setUserName(username);
        user.setUserScore(points);
        rankList.addUser(user);
        StoreGamesRank.getInstance(getContext()).setScoreList(rankList);
    }

    private void cardAnimation() {
        ObjectAnimator positioning = ObjectAnimator.ofFloat(
                picContainer,
                "translationX",
                0f, 200f
        );
        positioning.setDuration(250);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(
                picContainer,
                "alpha",
                1f, 0f
        );
        alpha.setDuration(250);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(positioning, alpha);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                changeShapeAndColor();
                cardAppearAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void cardAppearAnimation() {
        ObjectAnimator positioning = ObjectAnimator.ofFloat(
                picContainer,
                "translationX",
                -200f, 0f
        );
        positioning.setDuration(250);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(
                picContainer,
                "alpha",
                0f, 1f
        );
        alpha.setDuration(250);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(positioning, alpha);
        animatorSet.start();
    }

    private void alphaAnimation(View view) {
        ObjectAnimator increaseAlpha = ObjectAnimator.ofFloat(
                view,
                "alpha",
                0f, 1f
        );
        increaseAlpha.setDuration(500);
        increaseAlpha.start();
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
                "scaleY",
                1f, 1.3f, 1f
        );
        scaleYThat.setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(shakeThat, scaleXThat, scaleYThat);
        animatorSet.start();
    }

    private void popupAppearAnimation() {
        ObjectAnimator scaleXThat = ObjectAnimator.ofFloat(
                scorePopup,
                "scaleX",
                0f, 1f, 0.90f, 1f
        );
        scaleXThat.setDuration(750);

        ObjectAnimator scaleYThat = ObjectAnimator.ofFloat(
                scorePopup,
                "scaleY",
                0f, 1f, 0.90f, 1f
        );
        scaleYThat.setDuration(750);

        ObjectAnimator increaseAlpha = ObjectAnimator.ofFloat(
                scorePopup,
                "alpha",
                0f, 1f
        );
        increaseAlpha.setDuration(250);
        increaseAlpha.start();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXThat, scaleYThat, increaseAlpha);
        animatorSet.start();

        if (isBestScore) {
            animatorSet.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    bestScoreSignAnimation();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

    private void bestScoreSignAnimation() {
        ObjectAnimator scaleXThat = ObjectAnimator.ofFloat(
                bestScoreSign,
                "scaleX",
                5f, 1f
        );
        scaleXThat.setDuration(400);

        ObjectAnimator scaleYThat = ObjectAnimator.ofFloat(
                bestScoreSign,
                "scaleY",
                5f, 1f
        );
        scaleYThat.setDuration(400);

        ObjectAnimator increaseAlpha = ObjectAnimator.ofFloat(
                bestScoreSign,
                "alpha",
                0f, 1f
        );
        increaseAlpha.setDuration(200);
        increaseAlpha.start();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXThat, scaleYThat, increaseAlpha);
        animatorSet.start();
    }

    private void transactionToRankListFragment() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                    Log.i("Game TAG", "back stack cleared!");
                }

                RankListFragment rankListFragment = new RankListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", 2);
                bundle.putString("speed_match", "speed_match");
                rankListFragment.setArguments(bundle);

                try {

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_main_container, rankListFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    Log.i("Game TAG", "new fragment commited");

                } catch (NullPointerException e) {

                }
            }
        }, 5000);
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