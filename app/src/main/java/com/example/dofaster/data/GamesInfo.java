package com.example.dofaster.data;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

public class GamesInfo {

    private int gamePic;
    private String gameTitle;
    private String gameCaption;

    public GamesInfo(int gamePic, String gameTitle, String gameCaption) {
        this.gamePic = gamePic;
        this.gameTitle = gameTitle;
        this.gameCaption = gameCaption;
    }

    public int getGamePic() {
        return gamePic;
    }


    public String getGameTitle() {
        return gameTitle;
    }

    public String getGameCaption() {
        return gameCaption;
    }
}
