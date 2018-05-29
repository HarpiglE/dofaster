package com.example.dofaster.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class StoreGamesRank {

    private static StoreGamesRank instance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static String sharedPreferencesName;

    private StoreGamesRank(Context context) {
        sharedPreferences = context.getSharedPreferences("do_faster", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static StoreGamesRank getInstance(Context context) {
        if (instance == null) {
            instance = new StoreGamesRank(context);
        }
        return instance;
    }

    public static void changeSharedPreferencesName(String name) {
        sharedPreferencesName = name;
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
