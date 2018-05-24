package com.example.dofaster;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class StoreSpeedMatchScore {

    private static StoreSpeedMatchScore instance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private StoreSpeedMatchScore(Context context) {
        sharedPreferences = context.getSharedPreferences("Speed_Match_Rank_List", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static StoreSpeedMatchScore getInstance(Context context) {
        if (instance == null) {
            instance = new StoreSpeedMatchScore(context);
        }
        return instance;
    }

    public RankList getScoreList() {
        Gson gson = new Gson();
        String rankListJson = sharedPreferences.getString("Speed_Match_Rank_List", null);
        if (rankListJson == null) {
            return new RankList();
        }
        return gson.fromJson(rankListJson, RankList.class);
    }

    public void setScoreList(RankList rankList) {
        Gson gson = new Gson();
        String rankListJson = gson.toJson(rankList, RankList.class);
        editor.putString("Speed_Match_Rank_List", rankListJson);
        editor.apply();
    }
}
