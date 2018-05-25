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

    }

    private void setWelcomeMessage() {
//        userName = getIntent().getStringExtra("User_Name");
//        welcome.setText(getString(R.string.welcome_which, userName));
    }

    private void configureButtons() {

    }
}
