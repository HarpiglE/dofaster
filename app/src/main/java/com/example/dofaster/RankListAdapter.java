package com.example.dofaster;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dofaster.model.User;

import java.util.List;

public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.ViewHolder> {

    private List<User> rankList;

    public RankListAdapter(List<User> rankList) {
        this.rankList = rankList;
    }

    @Override
    public RankListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ranklist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankListAdapter.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.icon.setImageResource(R.drawable.gold_medal_sign);
                break;
            case 1:
                holder.icon.setImageResource(R.drawable.silver_medal_sign);
                break;
            case 2:
                holder.icon.setImageResource(R.drawable.bronze_medal_sign);
                break;
        }
        holder.rankNum.setText(String.valueOf(position + 1));
        holder.userName.setText(String.valueOf(rankList.get(position).getUserName()));
        holder.userScore.setText(String.valueOf(rankList.get(position).getUserScore()));
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView rankNum;
        private ImageView icon;
        private TextView userName;
        private TextView userScore;

        public ViewHolder(View itemView) {
            super(itemView);

            rankNum = itemView.findViewById(R.id.rank);
            icon = itemView.findViewById(R.id.icon);
            userName = itemView.findViewById(R.id.name);
            userScore = itemView.findViewById(R.id.score);
        }
    }
}
