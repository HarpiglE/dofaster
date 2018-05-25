package com.example.dofaster;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class StoreGamesRank {

    private static StoreGamesRank instance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String sharedPreferencesName;

    private StoreGamesRank(Context context, String sharedPreferencesName) {
        this.sharedPreferencesName = sharedPreferencesName;
        sharedPreferences = context.getSharedPreferences(
                sharedPreferencesName,
                Context.MODE_PRIVATE
        );
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static StoreGamesRank getInstance(Context context, String sharedPreferencesName) {
        if (instance == null) {
            instance = new StoreGamesRank(context, sharedPreferencesName);
        }
        return instance;
    }

    public RankList getScoreList() {
        Gson gson = new Gson();
        String rankListJson = sharedPreferences.getString(sharedPreferencesName, null);
        if (rankListJson == null) {
            return new RankList();
        }
        return gson.fromJson(rankListJson, RankList.class);
    }

    public void setScoreList(RankList rankList) {
        Gson gson = new Gson();
        String rankListJson = gson.toJson(rankList, RankList.class);
        editor.putString(sharedPreferencesName, rankListJson);
        editor.apply();
    }
}
