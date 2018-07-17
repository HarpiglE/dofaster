package com.example.dofaster.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dofaster.R;
import com.example.dofaster.RankListAdapter;
import com.example.dofaster.model.RankList;
import com.example.dofaster.model.StoreGamesRank;
import com.example.dofaster.model.User;

import java.util.Collections;
import java.util.Comparator;

public class RankListFragment extends Fragment {

    private RecyclerView rankList;

    private String sharedPreferencesName;

    public RankListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        if (getArguments() != null) {
            switch (getArguments().getInt("id")) {
                case 0:
                    sharedPreferencesName = getArguments().getString("chalkboard_challenge");
                    break;
                case 1:
                    sharedPreferencesName = getArguments().getString("color_match");
                    break;
                case 2:
                    sharedPreferencesName = getArguments().getString("speed_match");
                    break;
            }
        }
        return inflater.inflate(R.layout.fragment_rank_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findView(view);

        StoreGamesRank.changeSharedPreferencesName(sharedPreferencesName);

        if (StoreGamesRank.getInstance(getContext()).getScoreList().getRankList().size() == 0) {
            Toast.makeText(getActivity(),
                    getString(R.string.no_score),
                    Toast.LENGTH_SHORT)
                    .show();
        } else {
            setRecyclerView();
        }
    }

    private void findView(View view) {
        rankList = view.findViewById(R.id.rank_list_recycle_view);
    }

    private void setRecyclerView() {
        RankList rankListObject = StoreGamesRank.getInstance(getContext()).getScoreList();
        RankListAdapter rankListAdapter = new RankListAdapter(rankListObject.getRankList());

        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                if (user1.getUserScore() > user2.getUserScore()) {
                    return -1;
                } else if (user1.getUserScore() < user2.getUserScore()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
        Collections.sort(rankListObject.getRankList(), comparator);

        StoreGamesRank.getInstance(getContext()).setScoreList(rankListObject);

        rankList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rankList.setAdapter(rankListAdapter);
    }
}
