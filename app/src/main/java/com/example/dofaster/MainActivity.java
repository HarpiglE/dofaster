package com.example.dofaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button speedMatch;
    private Button whichOneIsLarger;
    private EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        configureButtons();
    }

    private void findView() {
        speedMatch = findViewById(R.id.speed_match_main_btn);
        whichOneIsLarger = findViewById(R.id.which_main_btn);
        userName = findViewById(R.id.user_name);
    }

    private void userNameEntered() {
        if (userName.getText().toString().equals("")) {
            userName.setText(getString(R.string.default_user_name));
        }
    }

    private void configureButtons() {
        speedMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameEntered();

                Intent intentToSpeedMatch = new Intent(MainActivity.this,
                        SpeedMatchActivity.class);
                intentToSpeedMatch.putExtra("User_Name", userName.getText().toString());
                startActivity(intentToSpeedMatch);
            }
        });

        whichOneIsLarger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameEntered();

                Intent intentToWhichOne = new Intent(MainActivity.this,
                        WhichOneIsLargerActivity.class);
                intentToWhichOne.putExtra("User_Name", userName.getText().toString());
                startActivity(intentToWhichOne);
            }
        });
    }
}
