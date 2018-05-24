package com.example.dofaster;

import java.util.ArrayList;
import java.util.List;

public class RankList {

    private List<User> rankList;

    public RankList() {
        rankList = new ArrayList<>();
    }

    public List<User> getRankList() {
        return rankList;
    }

    public void setRankList(List<User> rankList) {
        rankList = rankList;
    }

    public void addUser(User user) {
        rankList.add(user);
    }
}
