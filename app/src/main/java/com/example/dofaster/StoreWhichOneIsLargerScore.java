package com.example.dofaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class StoreWhichOneIsLargerScore {

    private static StoreWhichOneIsLargerScore instance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private StoreWhichOneIsLargerScore(Context context) {
        sharedPreferences = context.getSharedPreferences("Which_One_Is_Larger_Rank_List", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static StoreWhichOneIsLargerScore getInstance(Context context) {
        if (instance == null) {
            instance = new StoreWhichOneIsLargerScore(context);
        }
        return instance;
    }

    public RankList getScoreList() {
        Gson gson = new Gson();
        String rankListJson = sharedPreferences.getString("Which_One_Is_Larger_Rank_List", null);
        if (rankListJson == null) {
            return new RankList();
        }
        return gson.fromJson(rankListJson, RankList.class);
    }

    public void setScoreList(RankList rankList) {
        Gson gson = new Gson();
        String rankListJson = gson.toJson(rankList, RankList.class);
        editor.putString("Which_One_Is_Larger_Rank_List", rankListJson);
        editor.apply();
    }
}
