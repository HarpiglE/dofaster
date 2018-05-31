package com.example.dofaster;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dofaster.data.GamesInfo;
import com.example.dofaster.fragment.CMFragment;
import com.example.dofaster.fragment.RankListFragment;
import com.example.dofaster.fragment.SMFragment;
import com.example.dofaster.fragment.WOILFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText username;

    private String usernameSt;

    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    private MainActivityClickListener mainActivityClickListener = new MainActivityClickListener() {
        @Override
        public void whichClicked(int item, int id) {
            Bundle bundle = new Bundle();
            if (item == 0) {
                switch (id) {
                    case 0:
                        bundle.putString("username", usernameSt);
                        WOILFragment WOIL = new WOILFragment();
                        WOIL.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_main_container, WOIL)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        bundle.putString("username", usernameSt);
                        CMFragment CM = new CMFragment();
                        CM.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_main_container, CM)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 2:
                        bundle.putString("username", usernameSt);
                        SMFragment SM = new SMFragment();
                        SM.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_main_container, SM)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            } else {
                switch (id) {
                    case 0:
                        bundle.putInt("id", 0);
                        bundle.putString("chalkboard_challenge", "chalkboard_challenge");
                        RankListFragment fragmentZero = new RankListFragment();
                        fragmentZero.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_main_container, fragmentZero)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        bundle.putInt("id", 1);
                        bundle.putString("color_match", "color_match");
                        RankListFragment fragmentOne = new RankListFragment();
                        fragmentOne.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_main_container, fragmentOne)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 2:
                        bundle.putInt("id", 2);
                        bundle.putString("speed_match", "speed_match");
                        RankListFragment fragmentTwo = new RankListFragment();
                        fragmentTwo.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_main_container, fragmentTwo)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }
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
        gamesAdapter = new GamesAdapter(gamesInfoList, mainActivityClickListener);
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
        builder.setTitle(getString(R.string.dialog_title));
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

                        usernameSt = username.getText().toString();
                    }
                });
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }
}
