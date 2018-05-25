package com.example.dofaster;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dofaster.data.User;

import java.util.List;

public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.ViewHolder> {

    private List<User> rankList;
    private int rankCounter = 1;

    public RankListAdapter(List<User> rankList) {
        this.rankList = rankList;
    }

    @Override
    public RankListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranklist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankListAdapter.ViewHolder holder, int position) {
        holder.rankNum.setText(String.valueOf(rankCounter++));
        holder.userName.setText(String.valueOf(rankList.get(position).getUserName()));
        holder.userScore.setText(String.valueOf(rankList.get(position).getUserScore()));
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView rankNum;
        private TextView userName;
        private TextView userScore;

        public ViewHolder(View itemView) {
            super(itemView);

            rankNum = itemView.findViewById(R.id.rank);
            userName = itemView.findViewById(R.id.name);
            userScore = itemView.findViewById(R.id.score);
        }
    }
}
