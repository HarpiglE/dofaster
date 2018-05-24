package com.example.dofaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WhichOneIsLargerActivity extends AppCompatActivity {

    private TextView welcome;
    private Button letsGo;
    private Button scores;

    private String userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_one_is_larger);

        findViews();
        setWelcomeMessage();
        configureButtons();
    }

    private void findViews() {
        welcome = findViewById(R.id.which_one_welcome);
        letsGo = findViewById(R.id.start_which_one);
        scores = findViewById(R.id.which_one_scores_list);
    }

    private void setWelcomeMessage() {
        userName = getIntent().getStringExtra("User_Name");
        welcome.setText(getString(R.string.welcome_which, userName));
    }

    private void configureButtons() {
        letsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhichOneIsLargerFragment whichOneIsLargerFragment = new WhichOneIsLargerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("User_Name", userName);
                whichOneIsLargerFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.which_one_fragment, whichOneIsLargerFragment)
                        .commit();
            }
        });

        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.which_one_fragment, new WhichOneIsLargerScoreListFragment())
                        .commit();
            }
        });
    }
}
