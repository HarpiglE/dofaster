package com.example.dofaster.model;

public class GamesInfo {

    private int gamePic;
    private String gameTitle;

    public GamesInfo(int gamePic, String gameTitle) {
        this.gamePic = gamePic;
        this.gameTitle = gameTitle;
    }

    public int getGamePic() {
        return gamePic;
    }


    public String getGameTitle() {
        return gameTitle;
    }
}
