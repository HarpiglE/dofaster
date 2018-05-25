package com.example.dofaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dofaster.fragment.SpeedMatchFragment;
import com.example.dofaster.fragment.SpeedMatchScoreListFragment;

public class SpeedMatchActivity extends AppCompatActivity {

    private TextView welcome;
    private Button letsGo;
    private Button scores;

    private String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_match);

        findViews();
        setWelcomeMessage();
        configureButtons();
    }

    private void findViews() {
        welcome = findViewById(R.id.speed_match_welcome);
        letsGo = findViewById(R.id.start_speed_match);
        scores = findViewById(R.id.speed_match_scores_list);
    }

    private void setWelcomeMessage() {
        userName = getIntent().getStringExtra("User_Name");
        welcome.setText(getString(R.string.welcome_speed, userName));
    }

    private void configureButtons() {
        letsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SpeedMatchFragment speedMatchFragment = new SpeedMatchFragment();
                Bundle bundle = new Bundle();
                bundle.putString("User_Name", userName);
                speedMatchFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.speed_match_fragment, speedMatchFragment)
                        .commit();
            }
        });

        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.speed_match_fragment, new SpeedMatchScoreListFragment())
                        .commit();
            }
        });
    }
}
