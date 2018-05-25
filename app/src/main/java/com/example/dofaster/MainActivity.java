package com.example.dofaster;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText username;

    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    private String usernameSt;

    private ScoreListClickListener scoreListClickListener = new ScoreListClickListener() {
        @Override
        public void whichClicked(int id) {

        }
    };

    private RecyclerView recyclerView;
    private GamesAdapter gamesAdapter;
    private List<GamesInfo> gamesInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initialGamesList();
        configureRecycleView();
        configureDialog();
    }

    private void findViews() {
        recyclerView = findViewById(R.id.games_recycle_view);
    }

    private void initialGamesList() {
        GamesInfo chalkboardChallenge = new GamesInfo(
                R.drawable.header,
                getString(R.string.chalkboard_challenge_title),
                getString(R.string.chalkboard_challenge_caption)
        );

        GamesInfo colorMatch = new GamesInfo(
                R.drawable.header,
                getString(R.string.color_match_title),
                getString(R.string.color_match_caption)
        );

        GamesInfo speedMatch = new GamesInfo(
                R.drawable.header,
                getString(R.string.speed_match_title),
                getString(R.string.speed_match_caption)
        );

        gamesInfoList.add(chalkboardChallenge);
        gamesInfoList.add(colorMatch);
        gamesInfoList.add(speedMatch);
    }

    private void configureRecycleView() {
        gamesAdapter = new GamesAdapter(gamesInfoList, scoreListClickListener);
        recyclerView.setAdapter(gamesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void configureDialog() {
        // Setup the edit text in the dialog
        username = new EditText(MainActivity.this);
        username.setGravity(Gravity.CENTER);
        username.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        // Setup Dialog
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("نام خود را وارد کنید");
        builder.setView(username);
        builder.setPositiveButton(getString(R.string.confirm),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usernameSt = username.getText().toString();
                        if (usernameSt.equals("")) {
                            usernameSt = "ناشناس";
                        }
                        Toast.makeText(MainActivity.this,
                                getString(R.string.welcome, usernameSt),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }
}
